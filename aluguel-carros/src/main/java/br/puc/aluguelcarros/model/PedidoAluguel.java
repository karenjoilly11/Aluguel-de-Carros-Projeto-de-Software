package br.puc.aluguelcarros.model;

import jakarta.persistence.*;
import io.micronaut.core.annotation.Introspected;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Introspected
@Entity
@Table(name = "pedidos_aluguel")
public class PedidoAluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Double valorPrevisto;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    // --- Relacionamentos do Diagrama ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "automovel_id", nullable = false)
    private Automovel automovel;

    // Relacionamento 1:1 com Contrato (se o pedido for aprovado)
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Contrato contrato;

    public PedidoAluguel() {
        this.dataSolicitacao = LocalDateTime.now();
        this.status = StatusPedido.SOLICITADO;
    }

    // --- Métodos de Lógica de Negócio ---

    public double calcularValorTotal() {
        if (dataInicio != null && dataFim != null && automovel != null) {
            long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
            if (dias <= 0) dias = 1; // Mínimo de 1 diária
            return dias * automovel.getValorDiaria();
        }
        return valorPrevisto != null ? valorPrevisto : 0.0;
    }

    public void atualizarStatus(StatusPedido novoStatus) {
        this.status = novoStatus;
    }

    public boolean validarDatas() {
        if (dataInicio == null || dataFim == null) return false;
        return dataInicio.isBefore(dataFim) && dataInicio.isAfter(LocalDateTime.now());
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Automovel getAutomovel() { return automovel; }
    public void setAutomovel(Automovel automovel) { this.automovel = automovel; }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }

    public Double getValorPrevisto() { return valorPrevisto; }
    public void setValorPrevisto(Double valorPrevisto) { this.valorPrevisto = valorPrevisto; }

    public Contrato getContrato() { return contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }
    public Double getValorAluguel() {
    // Retorna o valor previsto ou calcula na hora
    return (this.valorPrevisto != null) ? this.valorPrevisto : calcularValorTotal();
}

}