package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AutomovelResponseDTO(
    String matricula,
    String marca,
    String modelo,
    Integer ano,
    String placa,
    Double valorDiaria,
    boolean disponivel
) {}