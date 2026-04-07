package br.puc.aluguelcarros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade central do CRUD da Sprint 02.
 *
 * Dados armazenados conforme especificação:
 *   - Identificação: RG, CPF, Nome, Endereço
 *   - Profissão
 *   - Entidades empregadoras (máx. 3, validado no Service)
 *   - Rendimentos auferidos (máx. 3, validado no Service)
 *
 * As anotações JPA (jakarta.persistence.*) são IDÊNTICAS às usadas
 * com Spring Boot/Hibernate — o Micronaut usa o mesmo provider JPA.
 * A diferença fica na camada de Repository e Controller.
 */
@Entity
@Table(name = "cliente",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_cliente_cpf", columnNames = "cpf"),
           @UniqueConstraint(name = "uk_cliente_rg",  columnNames = "rg")
       })
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Dados de identificação ────────────────────────────────────────
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF inválido")
    @Column(nullable = false, length = 14)
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Column(nullable = false, length = 20)
    private String rg;

    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false)
    private String endereco;

    // ── Dados profissionais ───────────────────────────────────────────
    @Column
    private String profissao;

    // ── Relacionamento: 1 cliente → N entidades empregadoras (máx. 3) ─
    // CascadeType.ALL garante que salvar/deletar o cliente
    // propaga automaticamente para as entidades filhas.
    // orphanRemoval remove entidades filhas se desvinculadas da lista.
    @OneToMany(mappedBy = "cliente",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    @Size(max = 3, message = "Máximo de 3 entidades empregadoras permitidas")
    private List<EntidadeEmpregadora> entidadesEmpregadoras = new ArrayList<>();

    // ── Relacionamento: 1 cliente → N rendimentos (máx. 3) ────────────
    @OneToMany(mappedBy = "cliente",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    @Size(max = 3, message = "Máximo de 3 rendimentos permitidos")
    private List<Rendimento> rendimentos = new ArrayList<>();

    // ── Construtores ─────────────────────────────────────────────────
    public Cliente() {}

    public Cliente(String nome, String cpf, String rg,
                   String endereco, String profissao) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.profissao = profissao;
    }

    // ── Getters / Setters ─────────────────────────────────────────────
    public Long getId() { return id; }

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

    public List<EntidadeEmpregadora> getEntidadesEmpregadoras() {
        return entidadesEmpregadoras;
    }
    public void setEntidadesEmpregadoras(List<EntidadeEmpregadora> list) {
        this.entidadesEmpregadoras = list;
    }

    public List<Rendimento> getRendimentos() { return rendimentos; }
    public void setRendimentos(List<Rendimento> rendimentos) {
        this.rendimentos = rendimentos;
    }
}
