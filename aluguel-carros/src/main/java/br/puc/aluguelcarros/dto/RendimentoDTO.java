package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO para receber e enviar dados de um rendimento.
 *
 * @Serdeable – anotação do Micronaut que registra esta classe no
 * mecanismo de serialização/deserialização em tempo de compilação.
 * Equivale a @JsonSerializable do Spring (implícito com Jackson).
 * É OBRIGATÓRIA no Micronaut quando a classe não é uma entidade JPA.
 */
@Serdeable
public class RendimentoDTO {

    private Long id;

    @NotNull(message = "Valor do rendimento é obrigatório")
    @DecimalMin(value = "0.01", message = "Rendimento deve ser maior que zero")
    private BigDecimal valor;

    private String tipoVinculo;

    /** Nome da entidade empregadora associada a este rendimento. */
    @NotBlank(message = "Nome da entidade empregadora é obrigatório")
    private String nomeEntidadeEmpregadora;

    private String cnpjEntidadeEmpregadora;

    // ── Construtores ─────────────────────────────────────────────────
    public RendimentoDTO() {}

    public RendimentoDTO(Long id, BigDecimal valor, String tipoVinculo,
                         String nomeEntidadeEmpregadora, String cnpjEntidadeEmpregadora) {
        this.id = id;
        this.valor = valor;
        this.tipoVinculo = tipoVinculo;
        this.nomeEntidadeEmpregadora = nomeEntidadeEmpregadora;
        this.cnpjEntidadeEmpregadora = cnpjEntidadeEmpregadora;
    }

    // ── Getters / Setters ─────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getTipoVinculo() { return tipoVinculo; }
    public void setTipoVinculo(String tipoVinculo) { this.tipoVinculo = tipoVinculo; }

    public String getNomeEntidadeEmpregadora() { return nomeEntidadeEmpregadora; }
    public void setNomeEntidadeEmpregadora(String nome) { this.nomeEntidadeEmpregadora = nome; }

    public String getCnpjEntidadeEmpregadora() { return cnpjEntidadeEmpregadora; }
    public void setCnpjEntidadeEmpregadora(String cnpj) { this.cnpjEntidadeEmpregadora = cnpj; }
}
