package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Introspected
public record AutomovelRequestDTO(
    @NotBlank String matricula,
    @NotBlank String marca,
    @NotBlank String modelo,
    @NotNull @Positive Integer ano,
    @NotBlank String placa,
    boolean disponivel
) {}