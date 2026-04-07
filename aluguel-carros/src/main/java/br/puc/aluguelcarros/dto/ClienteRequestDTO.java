package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO de entrada para criar ou atualizar um Cliente.
 * Separa os dados da API dos dados da entidade JPA,
 * evitando expor a entidade diretamente.
 *
 * @Serdeable → obrigatório no Micronaut para serialização AOT.
 * @Valid em listas → propaga a validação para os objetos filhos
 *        (mesmo comportamento do Spring Boot).
 */
@Serdeable
public class ClienteRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    private String rg;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    private String profissao;

    /**
     * Máximo de 3 rendimentos — regra de negócio validada também
     * no ClienteService para dupla garantia.
     */
    @Valid
    @Size(max = 3, message = "Máximo de 3 rendimentos permitidos")
    private List<RendimentoDTO> rendimentos = new ArrayList<>();

    // ── Getters / Setters ─────────────────────────────────────────────
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }

    public List<RendimentoDTO> getRendimentos() { return rendimentos; }
    public void setRendimentos(List<RendimentoDTO> rendimentos) { this.rendimentos = rendimentos; }
}
