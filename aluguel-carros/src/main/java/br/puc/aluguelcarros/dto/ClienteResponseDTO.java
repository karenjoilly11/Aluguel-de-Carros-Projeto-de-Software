package br.puc.aluguelcarros.dto;

import br.puc.aluguelcarros.model.Cliente;
import br.puc.aluguelcarros.model.Rendimento;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO de saída — representa o Cliente serializado na resposta HTTP.
 * Exposição controlada: campos sensíveis (RG completo, por ex.) podem
 * ser omitidos ou mascarados aqui sem alterar a entidade JPA.
 */
@Serdeable
public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private String endereco;
    private String profissao;
    private List<RendimentoDTO> rendimentos;

    /** Factory method: converte a entidade JPA para o DTO de resposta. */
    public static ClienteResponseDTO from(Cliente c) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.id        = c.getId();
        dto.nome      = c.getNome();
        dto.cpf       = c.getCpf();
        dto.rg        = c.getRg();
        dto.endereco  = c.getEndereco();
        dto.profissao = c.getProfissao();
        dto.rendimentos = c.getRendimentos().stream()
                .map(r -> toRendimentoDTO(r))
                .toList();
        return dto;
    }

    private static RendimentoDTO toRendimentoDTO(Rendimento r) {
        String nomeEmp = r.getEntidadeEmpregadora() != null
                ? r.getEntidadeEmpregadora().getNome() : null;
        String cnpjEmp = r.getEntidadeEmpregadora() != null
                ? r.getEntidadeEmpregadora().getCnpj() : null;
        return new RendimentoDTO(r.getId(), r.getValor(),
                r.getTipoVinculo(), nomeEmp, cnpjEmp);
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getRg() { return rg; }
    public String getEndereco() { return endereco; }
    public String getProfissao() { return profissao; }
    public List<RendimentoDTO> getRendimentos() { return rendimentos; }
}
