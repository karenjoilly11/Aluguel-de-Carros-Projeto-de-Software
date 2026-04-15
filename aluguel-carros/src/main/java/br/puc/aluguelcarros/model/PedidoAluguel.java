package br.puc.aluguelcarros.model;

import jakarta.persistence.*;
import io.micronaut.core.annotation.Introspected;
import java.time.LocalDateTime;

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

    // Construtor padrão
    public PedidoAluguel() {}

    // Métodos do Diagrama (Lógica de Negócio básica)
    public double calcularValorTotal() {
        // Exemplo: lógica baseada na diferença de dias
        return valorPrevisto != null ? valorPrevisto : 0.0;
    }

    public void atualizarStatus(StatusPedido novoStatus) {
        this.status = novoStatus;
    }

    public boolean validarDatas() {
        if (dataInicio == null || dataFim == null) return false;
        return dataInicio.isBefore(dataFim);
    }

    // Getters e Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Double getValorPrevisto() {
        return valorPrevisto;
    }

    public void setValorPrevisto(Double valorPrevisto) {
        this.valorPrevisto = valorPrevisto;
    }
    
}