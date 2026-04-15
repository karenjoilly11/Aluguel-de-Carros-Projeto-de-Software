package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;
import java.time.LocalDateTime;

@Introspected
public record PedidoRequestDTO(
    LocalDateTime dataInicio,
    LocalDateTime dataFim,
    Double valorPrevisto,
    String matriculaAutomovel, // Referência ao carro
    Long clienteId             // Referência ao cliente
) {}