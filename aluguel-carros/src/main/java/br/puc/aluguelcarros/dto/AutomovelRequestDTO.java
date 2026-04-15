package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Serdeable
public record AutomovelRequestDTO(
    @NotBlank String matricula,
    @NotBlank String marca,
    @NotBlank String modelo,
    Integer ano,
    @NotBlank String placa,
    @Positive Double valorDiaria, // Adicionado para consistência com PedidoAluguel
    boolean disponivel
) {}