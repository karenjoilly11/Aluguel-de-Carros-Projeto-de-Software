package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.dto.ClienteRequestDTO;
import br.puc.aluguelcarros.dto.ClienteResponseDTO;
import br.puc.aluguelcarros.model.Cliente;
import br.puc.aluguelcarros.model.EntidadeEmpregadora;
import br.puc.aluguelcarros.model.Rendimento;
import br.puc.aluguelcarros.repository.ClienteRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Camada de serviço responsável pelas regras de negócio do CRUD de Cliente.
 *
 * DIFERENÇA vs Spring Boot:
 *   Spring Boot → @Service (org.springframework.stereotype.Service)
 *   Micronaut   → @Singleton (jakarta.inject.Singleton)
 *                 O Micronaut também suporta @Service via extensão,
 *                 mas @Singleton é o padrão JSR-330 do framework.
 *
 * @Transactional (jakarta.transaction.Transactional) funciona da mesma
 * forma que no Spring, mas é processado pelo interceptor do Micronaut.
 *
 * Injeção de dependência via construtor (recomendada em ambos os frameworks).
 */
@Singleton
public class ClienteService {

    private static final int MAX_RENDIMENTOS = 3;

    private final ClienteRepository clienteRepository;

    // Micronaut injeta automaticamente via construtor — sem @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // ── CREATE ────────────────────────────────────────────────────────

    /**
     * Cadastra um novo cliente com seus rendimentos e entidades empregadoras.
     *
     * Regras de negócio aplicadas:
     *   1. CPF único no sistema
     *   2. RG único no sistema
     *   3. Máximo de 3 rendimentos
     */
    @Transactional
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto) {
        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + dto.getCpf());
        }
        if (clienteRepository.existsByRg(dto.getRg())) {
            throw new IllegalArgumentException("RG já cadastrado: " + dto.getRg());
        }
        if (dto.getRendimentos() != null && dto.getRendimentos().size() > MAX_RENDIMENTOS) {
            throw new IllegalArgumentException(
                    "Máximo de " + MAX_RENDIMENTOS + " rendimentos permitidos por cliente.");
        }

        Cliente cliente = new Cliente(
                dto.getNome(),
                dto.getCpf(),
                dto.getRg(),
                dto.getEndereco(),
                dto.getProfissao()
        );

        // Persiste o cliente primeiro para obter o ID gerado
        cliente = clienteRepository.save(cliente);

        // Cria e vincula as entidades empregadoras e rendimentos
        if (dto.getRendimentos() != null) {
            for (var rendDTO : dto.getRendimentos()) {
                EntidadeEmpregadora empregadora = new EntidadeEmpregadora(
                        rendDTO.getNomeEntidadeEmpregadora(),
                        rendDTO.getCnpjEntidadeEmpregadora(),
                        null,
                        cliente
                );
                cliente.getEntidadesEmpregadoras().add(empregadora);

                Rendimento rendimento = new Rendimento(
                        rendDTO.getValor(),
                        rendDTO.getTipoVinculo(),
                        cliente,
                        empregadora
                );
                cliente.getRendimentos().add(rendimento);
            }
            // Atualiza com as coleções preenchidas (cascade persiste os filhos)
            cliente = clienteRepository.update(cliente);
        }

        return ClienteResponseDTO.from(cliente);
    }

    // ── READ ALL ──────────────────────────────────────────────────────

    @Transactional
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::from)
                .toList();
    }

    // ── READ BY ID ────────────────────────────────────────────────────

    @Transactional
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado: id=" + id));
        return ClienteResponseDTO.from(cliente);
    }

    // ── UPDATE ────────────────────────────────────────────────────────

    /**
     * Atualiza os dados editáveis do cliente: endereço e profissão.
     * CPF e RG não são alteráveis após o cadastro (imutabilidade de identificação).
     */
    @Transactional
    public ClienteResponseDTO atualizarDados(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado: id=" + id));

        cliente.setNome(dto.getNome());
        cliente.setEndereco(dto.getEndereco());
        cliente.setProfissao(dto.getProfissao());

        if (dto.getRendimentos() != null && dto.getRendimentos().size() > MAX_RENDIMENTOS) {
            throw new IllegalArgumentException(
                    "Máximo de " + MAX_RENDIMENTOS + " rendimentos permitidos por cliente.");
        }

        // Substitui a lista de rendimentos e empregadoras
        // orphanRemoval=true no @OneToMany cuida da exclusão dos antigos
        cliente.getRendimentos().clear();
        cliente.getEntidadesEmpregadoras().clear();

        if (dto.getRendimentos() != null) {
            for (var rendDTO : dto.getRendimentos()) {
                EntidadeEmpregadora empregadora = new EntidadeEmpregadora(
                        rendDTO.getNomeEntidadeEmpregadora(),
                        rendDTO.getCnpjEntidadeEmpregadora(),
                        null,
                        cliente
                );
                cliente.getEntidadesEmpregadoras().add(empregadora);

                Rendimento rendimento = new Rendimento(
                        rendDTO.getValor(),
                        rendDTO.getTipoVinculo(),
                        cliente,
                        empregadora
                );
                cliente.getRendimentos().add(rendimento);
            }
        }

        return ClienteResponseDTO.from(clienteRepository.update(cliente));
    }

    // ── DELETE ────────────────────────────────────────────────────────

    /**
     * Remove o cadastro do cliente e seus dados dependentes
     * (rendimentos e entidades empregadoras via cascade).
     */
    @Transactional
    public void excluirCadastro(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NoSuchElementException("Cliente não encontrado: id=" + id);
        }
        clienteRepository.deleteById(id);
    }
}
