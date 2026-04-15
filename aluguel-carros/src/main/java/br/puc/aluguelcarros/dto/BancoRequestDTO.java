package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record BancoRequestDTO(
    String cnpj,
    String endereco,
    String profissao
) {}