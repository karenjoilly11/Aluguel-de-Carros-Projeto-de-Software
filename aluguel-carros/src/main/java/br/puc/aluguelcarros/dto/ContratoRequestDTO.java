package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record ContratoRequestDTO(
    Long pedidoId,
    String termos,
    double valorFinal,
    Long bancoId
) {}