package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ContratoResponseDTO(
    Long id,
    String termos,
    Double valorFinal,
    boolean assinado,
    boolean possuiCredito,
    Long pedidoId
) {}