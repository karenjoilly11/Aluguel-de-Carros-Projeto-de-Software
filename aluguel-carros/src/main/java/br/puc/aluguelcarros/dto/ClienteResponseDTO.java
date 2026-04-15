package br.puc.aluguelcarros.dto;

import br.puc.aluguelcarros.model.Cliente;
import br.puc.aluguelcarros.model.Rendimento;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

/**
 * DTO de saída — ajustado para o modelo da Sprint 2.
 * Removido o campo RG (não consta no diagrama) e simplificado o Rendimento.
 */
@Serdeable
public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String email; // Adicionado conforme herança de Usuario
    private String cpf;
    private String endereco;
    private String profissao;
    private List<RendimentoDTO> rendimentos;

    /** Factory method: converte a entidade JPA para o DTO de resposta. */
    public static ClienteResponseDTO from(Cliente c) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.id        = c.getId();
        dto.nome      = c.getNome();
        dto.email     = c.getEmail(); // Campo vindo da classe pai Usuario
        dto.cpf       = c.getCpf();
        dto.endereco  = c.getEndereco();
        dto.profissao = c.getProfissao();
        
        if (c.getRendimentos() != null) {
            dto.rendimentos = c.getRendimentos().stream()
                    .map(ClienteResponseDTO::toRendimentoDTO)
                    .toList();
        }
        return dto;
    }

    private static RendimentoDTO toRendimentoDTO(Rendimento r) {
        // Mapeamento direto: a empregadora agora é uma String no próprio Rendimento
        return new RendimentoDTO(
                r.getId(), 
                r.getValor(),
                r.getEntidadeEmpregadora() 
        );
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }
    public String getEndereco() { return endereco; }
    public String getProfissao() { return profissao; }
    public List<RendimentoDTO> getRendimentos() { return rendimentos; }
}