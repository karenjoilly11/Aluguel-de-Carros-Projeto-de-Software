package br.puc.aluguelcarros.model;

import jakarta.persistence.*;
import io.micronaut.core.annotation.Introspected;

@Introspected
@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String termos;
    private double valorFinal;
    private boolean assinado;
    private boolean possuiCredito;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private PedidoAluguel pedidoAluguel;

    @ManyToOne
    @JoinColumn(name = "banco_id")
    private Banco banco;

    public Contrato() {}

    // Métodos do Diagrama
    public void registrarAssinatura() {
        this.assinado = true;
    }

    public void vincularCreditoBancario(Banco banco) {
        this.banco = banco;
        this.possuiCredito = (banco != null);
    }

    public String imprimirContrato() {
        return "Contrato ID: " + id + " | Termos: " + termos + " | Valor: R$ " + valorFinal;
    }

    // Getters e Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getValorFinal() { return valorFinal; }
    public void setValorFinal(double valorFinal) { this.valorFinal = valorFinal; }
    public PedidoAluguel getPedidoAluguel() { return pedidoAluguel; }
    public void setPedidoAluguel(PedidoAluguel pedidoAluguel) { this.pedidoAluguel = pedidoAluguel; }

    public String getTermos() {
        return termos;
    }

    public void setTermos(String termos) {
        this.termos = termos;
    }

    public boolean isAssinado() {
        return assinado;
    }

    public void setAssinado(boolean assinado) {
        this.assinado = assinado;
    }

    public boolean isPossuiCredito() {
        return possuiCredito;
    }

    public void setPossuiCredito(boolean possuiCredito) {
        this.possuiCredito = possuiCredito;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
}