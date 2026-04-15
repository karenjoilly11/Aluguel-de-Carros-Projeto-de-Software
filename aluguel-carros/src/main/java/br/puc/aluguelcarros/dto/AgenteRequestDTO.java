package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Serdeable
public record AgenteRequestDTO(
    // Campos de Usuário (Necessários para criar o Agente no banco)
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 6) String senha,

    // Campos específicos do Agente
    @NotBlank String cpf,
    String endereco,
    String profissao
) {}