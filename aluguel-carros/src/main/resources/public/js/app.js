/**
 * app.js — Frontend do Sistema de Aluguel de Carros
 * Conecta com o backend Micronaut via Fetch API (REST).
 *
 * Endpoints consumidos:
 *   GET    /clientes        → listar todos
 *   POST   /clientes        → cadastrar
 *   PUT    /clientes/{id}   → atualizar
 *   DELETE /clientes/{id}   → excluir
 */

const API = '/clientes';

// ── Instâncias dos modais Bootstrap ──────────────────────────────────
const modalCliente = new bootstrap.Modal(document.getElementById('modalCliente'));
const modalExcluir = new bootstrap.Modal(document.getElementById('modalExcluir'));
const toastEl     = document.getElementById('toast');
const bsToast     = new bootstrap.Toast(toastEl, { delay: 3500 });

// ── Contador de rendimentos no formulário ─────────────────────────────
let contadorRend = 0;

// ════════════════════════════════════════════════════════════════════
// INICIALIZAÇÃO
// ════════════════════════════════════════════════════════════════════
document.addEventListener('DOMContentLoaded', carregarClientes);

// ════════════════════════════════════════════════════════════════════
// LISTAGEM — GET /clientes
// ════════════════════════════════════════════════════════════════════
async function carregarClientes() {
  const tbody = document.getElementById('corpoTabela');
  tbody.innerHTML = `
    <tr><td colspan="8" class="text-center py-5 text-muted">
      <div class="spinner-border spinner-border-sm me-2"></div>Carregando...
    </td></tr>`;

  try {
    const res = await fetch(API);
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    const clientes = await res.json();
    renderizarTabela(clientes);
  } catch (err) {
    tbody.innerHTML = `
      <tr><td colspan="8" class="text-center py-5 text-danger">
        <i class="bi bi-wifi-off fs-4 d-block mb-2"></i>
        Não foi possível conectar ao servidor.<br>
        <small class="text-muted">${err.message}</small>
      </td></tr>`;
  }
}

function renderizarTabela(clientes) {
  const tbody = document.getElementById('corpoTabela');

  if (clientes.length === 0) {
    tbody.innerHTML = `
      <tr><td colspan="8" class="text-center py-5 text-muted">
        <i class="bi bi-inbox fs-2 d-block mb-2"></i>
        Nenhum cliente cadastrado.
      </td></tr>`;
    return;
  }

  tbody.innerHTML = clientes.map(c => {
    const rendTotal = c.rendimentos
      ? c.rendimentos.reduce((s, r) => s + Number(r.valor), 0) : 0;
    const rendBadge = c.rendimentos?.length > 0
      ? `<span class="badge badge-rend">${c.rendimentos.length} fonte(s) · R$ ${formatarMoeda(rendTotal)}</span>`
      : `<span class="text-muted small">—</span>`;

    return `
      <tr>
        <td class="ps-4 text-muted small">${c.id}</td>
        <td class="fw-semibold">${esc(c.nome)}</td>
        <td><span class="font-mono">${esc(c.cpf)}</span></td>
        <td><span class="font-mono">${esc(c.rg)}</span></td>
        <td class="text-truncate" style="max-width:180px" title="${esc(c.endereco)}">${esc(c.endereco)}</td>
        <td>${esc(c.profissao || '—')}</td>
        <td class="text-center">${rendBadge}</td>
        <td class="text-center pe-4">
          <button class="btn btn-sm btn-outline-primary me-1"
                  onclick="abrirModalEdicao(${c.id})"
                  title="Editar">
            <i class="bi bi-pencil"></i>
          </button>
          <button class="btn btn-sm btn-outline-danger"
                  onclick="confirmarExclusao(${c.id}, '${esc(c.nome)}')"
                  title="Excluir">
            <i class="bi bi-trash3"></i>
          </button>
        </td>
      </tr>`;
  }).join('');
}

// ════════════════════════════════════════════════════════════════════
// MODAL CADASTRO — abre vazio
// ════════════════════════════════════════════════════════════════════
function abrirModalCadastro() {
  document.getElementById('modalTitulo').innerHTML =
    '<i class="bi bi-person-plus-fill me-2"></i>Novo Cliente';
  limparFormulario();
  modalCliente.show();
}

// ════════════════════════════════════════════════════════════════════
// MODAL EDIÇÃO — GET /clientes/{id} e preenche formulário
// ════════════════════════════════════════════════════════════════════
async function abrirModalEdicao(id) {
  document.getElementById('modalTitulo').innerHTML =
    '<i class="bi bi-pencil-square me-2"></i>Editar Cliente';
  limparFormulario();

  try {
    const res = await fetch(`${API}/${id}`);
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    const c = await res.json();

    document.getElementById('clienteId').value  = c.id;
    document.getElementById('nome').value        = c.nome;
    document.getElementById('cpf').value         = c.cpf;
    document.getElementById('rg').value          = c.rg;
    document.getElementById('endereco').value    = c.endereco;
    document.getElementById('profissao').value   = c.profissao || '';

    // CPF e RG não podem ser editados após cadastro
    document.getElementById('cpf').readOnly = true;
    document.getElementById('rg').readOnly  = true;

    // Preenche rendimentos existentes
    if (c.rendimentos) {
      c.rendimentos.forEach(r => adicionarRendimento(r));
    }

    modalCliente.show();
  } catch (err) {
    mostrarToast('Erro ao carregar cliente.', 'danger');
  }
}

// ════════════════════════════════════════════════════════════════════
// SALVAR — POST (novo) ou PUT (edição)
// ════════════════════════════════════════════════════════════════════
async function salvarCliente() {
  const form = document.getElementById('formCliente');
  if (!form.checkValidity()) {
    form.classList.add('was-validated');
    return;
  }

  const id = document.getElementById('clienteId').value;
  const payload = montarPayload();

  const url    = id ? `${API}/${id}` : API;
  const method = id ? 'PUT' : 'POST';

  try {
    const res = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (res.status === 409) {
      mostrarToast('CPF ou RG já cadastrado no sistema.', 'danger');
      return;
    }
    if (!res.ok) throw new Error(`HTTP ${res.status}`);

    modalCliente.hide();
    mostrarToast(id ? 'Cliente atualizado com sucesso!' : 'Cliente cadastrado com sucesso!', 'success');
    carregarClientes();
  } catch (err) {
    mostrarToast('Erro ao salvar cliente: ' + err.message, 'danger');
  }
}

function montarPayload() {
  const rendimentos = [];
  document.querySelectorAll('.card-rendimento').forEach(card => {
    const idx = card.dataset.idx;
    const val = document.getElementById(`rendValor_${idx}`)?.value;
    const vin = document.getElementById(`rendVinculo_${idx}`)?.value;
    const emp = document.getElementById(`rendEmpregadora_${idx}`)?.value;
    const cnp = document.getElementById(`rendCnpj_${idx}`)?.value;

    if (val && emp) {
      rendimentos.push({
        valor: parseFloat(val.replace(',', '.')),
        tipoVinculo: vin || null,
        nomeEntidadeEmpregadora: emp,
        cnpjEntidadeEmpregadora: cnp || null
      });
    }
  });

  return {
    nome:      document.getElementById('nome').value.trim(),
    cpf:       document.getElementById('cpf').value.replace(/\D/g, ''),
    rg:        document.getElementById('rg').value.trim(),
    endereco:  document.getElementById('endereco').value.trim(),
    profissao: document.getElementById('profissao').value.trim() || null,
    rendimentos
  };
}

// ════════════════════════════════════════════════════════════════════
// EXCLUSÃO — DELETE /clientes/{id}
// ════════════════════════════════════════════════════════════════════
function confirmarExclusao(id, nome) {
  document.getElementById('nomeExcluir').textContent = nome;
  document.getElementById('btnConfirmarExcluir').onclick = () => excluirCliente(id);
  modalExcluir.show();
}

async function excluirCliente(id) {
  try {
    const res = await fetch(`${API}/${id}`, { method: 'DELETE' });
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    modalExcluir.hide();
    mostrarToast('Cliente excluído com sucesso.', 'success');
    carregarClientes();
  } catch (err) {
    mostrarToast('Erro ao excluir cliente: ' + err.message, 'danger');
  }
}

// ════════════════════════════════════════════════════════════════════
// RENDIMENTOS — adicionar / remover cards dinamicamente
// ════════════════════════════════════════════════════════════════════
function adicionarRendimento(dados = null) {
  const lista = document.getElementById('listaRendimentos');
  const qtd   = lista.querySelectorAll('.card-rendimento').length;

  if (qtd >= 3) {
    mostrarToast('Máximo de 3 rendimentos permitidos.', 'warning');
    return;
  }

  const idx = ++contadorRend;
  const card = document.createElement('div');
  card.className = 'card card-rendimento mb-2';
  card.dataset.idx = idx;
  card.innerHTML = `
    <div class="card-body py-2 px-3">
      <div class="d-flex justify-content-between align-items-center mb-2">
        <small class="fw-semibold text-muted">Rendimento ${qtd + 1}</small>
        <button type="button" class="btn btn-sm btn-link text-danger p-0"
                onclick="removerRendimento(this, ${idx})">
          <i class="bi bi-x-lg"></i>
        </button>
      </div>
      <div class="row g-2">
        <div class="col-md-4">
          <label class="form-label small">Valor mensal (R$) *</label>
          <input type="number" step="0.01" min="0.01" class="form-control form-control-sm"
                 id="rendValor_${idx}" value="${dados?.valor ?? ''}" required
                 placeholder="0,00"/>
        </div>
        <div class="col-md-4">
          <label class="form-label small">Tipo de vínculo</label>
          <select class="form-select form-select-sm" id="rendVinculo_${idx}">
            <option value="">Selecione</option>
            <option value="CLT"       ${dados?.tipoVinculo === 'CLT'      ? 'selected' : ''}>CLT</option>
            <option value="PJ"        ${dados?.tipoVinculo === 'PJ'       ? 'selected' : ''}>PJ</option>
            <option value="Autonomo"  ${dados?.tipoVinculo === 'Autonomo' ? 'selected' : ''}>Autônomo</option>
            <option value="Servidor"  ${dados?.tipoVinculo === 'Servidor' ? 'selected' : ''}>Servidor Público</option>
            <option value="Outro"     ${dados?.tipoVinculo === 'Outro'    ? 'selected' : ''}>Outro</option>
          </select>
        </div>
        <div class="col-md-4">
          <label class="form-label small">Entidade empregadora *</label>
          <input type="text" class="form-control form-control-sm"
                 id="rendEmpregadora_${idx}" required
                 value="${esc(dados?.nomeEntidadeEmpregadora ?? '')}"
                 placeholder="Nome da empresa"/>
        </div>
        <div class="col-md-4">
          <label class="form-label small">CNPJ da empregadora</label>
          <input type="text" class="form-control form-control-sm"
                 id="rendCnpj_${idx}"
                 value="${esc(dados?.cnpjEntidadeEmpregadora ?? '')}"
                 placeholder="00.000.000/0001-00"/>
        </div>
      </div>
    </div>`;

  lista.appendChild(card);
  atualizarEstadoBotaoRend();
}

function removerRendimento(btn, idx) {
  btn.closest('.card-rendimento').remove();
  atualizarEstadoBotaoRend();
}

function atualizarEstadoBotaoRend() {
  const qtd  = document.querySelectorAll('.card-rendimento').length;
  const msg  = document.getElementById('msgSemRend');
  const btnA = document.getElementById('btnAddRend');
  msg.style.display  = qtd === 0 ? 'block' : 'none';
  btnA.disabled      = qtd >= 3;
}

// ════════════════════════════════════════════════════════════════════
// UTILITÁRIOS
// ════════════════════════════════════════════════════════════════════
function limparFormulario() {
  const form = document.getElementById('formCliente');
  form.reset();
  form.classList.remove('was-validated');
  document.getElementById('clienteId').value = '';
  document.getElementById('cpf').readOnly    = false;
  document.getElementById('rg').readOnly     = false;
  document.getElementById('listaRendimentos').innerHTML = '';
  contadorRend = 0;
  atualizarEstadoBotaoRend();
}

function mostrarToast(msg, tipo = 'success') {
  const cores = {
    success: 'bg-success text-white',
    danger:  'bg-danger text-white',
    warning: 'bg-warning text-dark'
  };
  toastEl.className = `toast align-items-center border-0 ${cores[tipo] || cores.success}`;
  document.getElementById('toastMsg').textContent = msg;
  bsToast.show();
}

function mascararCPF(input) {
  let v = input.value.replace(/\D/g, '').substring(0, 11);
  v = v.replace(/(\d{3})(\d)/, '$1.$2');
  v = v.replace(/(\d{3})(\d)/, '$1.$2');
  v = v.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
  input.value = v;
}

function formatarMoeda(val) {
  return Number(val).toLocaleString('pt-BR', { minimumFractionDigits: 2 });
}

/** Escapa HTML para evitar XSS ao inserir dados do servidor no DOM. */
function esc(str) {
  if (str == null) return '';
  return String(str)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}
