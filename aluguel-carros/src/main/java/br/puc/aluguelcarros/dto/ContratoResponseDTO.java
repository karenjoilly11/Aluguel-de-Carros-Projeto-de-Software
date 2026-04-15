package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record ContratoResponseDTO(
    Long id,
    String termos,
    double valorFinal,
    boolean assinado,
    boolean possuiCredito,
    Long pedidoId
) {}
