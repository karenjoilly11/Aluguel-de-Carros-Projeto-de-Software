package br.puc.aluguelcarros.dto;
import io.micronaut.core.annotation.Introspected;

@Introspected
public record AgenteResponseDTO(
    Long id,
    String cpf,
    String endereco,
    String profissao
) {}