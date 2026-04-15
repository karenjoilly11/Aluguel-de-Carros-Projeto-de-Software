package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Serdeable
public record RendimentoDTO(
    Long id,
    @NotNull @Positive BigDecimal valor,
    String nomeEntidadeEmpregadora
) {}