package br.puc.aluguelcarros.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record EmpresaRequestDTO(
    String cnpj, // Usando o campo CPF do pai para armazenar o identificador
    String endereco,
    String profissao
) {}