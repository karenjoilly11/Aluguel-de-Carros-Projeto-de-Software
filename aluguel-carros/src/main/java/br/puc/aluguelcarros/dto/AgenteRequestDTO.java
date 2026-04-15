package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record AgenteRequestDTO(
    String cpf,
    String endereco,
    String profissao
) {}