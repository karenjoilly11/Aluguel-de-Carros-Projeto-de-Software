package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record AutomovelResponseDTO(
    String matricula,
    String marca,
    String modelo,
    Integer ano,
    String placa,
    boolean disponivel
) {}