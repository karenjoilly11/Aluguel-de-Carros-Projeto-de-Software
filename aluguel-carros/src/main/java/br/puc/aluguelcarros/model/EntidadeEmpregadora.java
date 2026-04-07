package br.puc.aluguelcarros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Representa uma das entidades empregadoras do cliente.
 * Um cliente pode ter no máximo 3 fontes de renda, cada uma
 * vinculada a uma EntidadeEmpregadora e a um Rendimento.
 *
 * Mapeamento JPA idêntico ao Spring Boot (jakarta.persistence.*).
 */
@Entity
@Table(name = "entidade_empregadora")
public class EntidadeEmpregadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da entidade empregadora é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Column
    private String cnpj;

    @Column
    private String setor;

    // ── Relacionamento: N empregadoras → 1 cliente ───────────────────
    // @ManyToOne com LAZY loading para evitar N+1 queries
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // ── Construtores ─────────────────────────────────────────────────
    public EntidadeEmpregadora() {}

    public EntidadeEmpregadora(String nome, String cnpj, String setor, Cliente cliente) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.setor = setor;
        this.cliente = cliente;
    }

    // ── Getters / Setters ─────────────────────────────────────────────
    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
