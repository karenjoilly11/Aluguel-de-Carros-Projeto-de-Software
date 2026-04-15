package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;

/**
 * DTO de resposta para Agente.
 * @Serdeable é necessário para serialização JSON no Micronaut.
 */
@Serdeable
public record AgenteResponseDTO(
    Long id,
    String nome,     // Campo herdado de Usuario
    String email,    // Campo herdado de Usuario
    String cpf,
    String endereco,
    String profissao
) {}