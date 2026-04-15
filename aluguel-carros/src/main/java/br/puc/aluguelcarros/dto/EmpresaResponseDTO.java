package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record EmpresaResponseDTO(
    Long id,
    String cnpj,
    String endereco,
    String profissao
) {}