package br.puc.aluguelcarros.dto;

import br.puc.aluguelcarros.model.StatusPedido;
import io.micronaut.core.annotation.Introspected;
import java.time.LocalDateTime;

@Introspected
public record PedidoResponseDTO(
    Long id,
    LocalDateTime dataSolicitacao,
    LocalDateTime dataInicio,
    LocalDateTime dataFim,
    Double valorPrevisto,
    StatusPedido status
) {}