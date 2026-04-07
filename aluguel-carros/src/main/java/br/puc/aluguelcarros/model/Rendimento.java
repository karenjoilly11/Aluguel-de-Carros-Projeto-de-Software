package br.puc.aluguelcarros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Representa um rendimento mensal do cliente vinculado a uma
 * entidade empregadora.
 *
 * Regra de negócio: máximo de 3 rendimentos por cliente.
 * Essa regra é validada na camada de serviço (ClienteService).
 */
@Entity
@Table(name = "rendimento")
public class Rendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Valor do rendimento é obrigatório")
    @DecimalMin(value = "0.01", message = "Rendimento deve ser maior que zero")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    /** Tipo de vínculo: CLT, PJ, Autônomo, etc. */
    @Column
    private String tipoVinculo;

    // ── Relacionamento: N rendimentos → 1 cliente ────────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // ── Relacionamento: N rendimentos → 1 empregadora ─────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidade_empregadora_id")
    private EntidadeEmpregadora entidadeEmpregadora;

    // ── Construtores ─────────────────────────────────────────────────
    public Rendimento() {}

    public Rendimento(BigDecimal valor, String tipoVinculo,
                      Cliente cliente, EntidadeEmpregadora entidadeEmpregadora) {
        this.valor = valor;
        this.tipoVinculo = tipoVinculo;
        this.cliente = cliente;
        this.entidadeEmpregadora = entidadeEmpregadora;
    }

    // ── Getters / Setters ─────────────────────────────────────────────
    public Long getId() { return id; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getTipoVinculo() { return tipoVinculo; }
    public void setTipoVinculo(String tipoVinculo) { this.tipoVinculo = tipoVinculo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public EntidadeEmpregadora getEntidadeEmpregadora() { return entidadeEmpregadora; }
    public void setEntidadeEmpregadora(EntidadeEmpregadora e) { this.entidadeEmpregadora = e; }
}
