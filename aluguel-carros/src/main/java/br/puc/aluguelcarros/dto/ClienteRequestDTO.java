package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Serdeable
public record ClienteRequestDTO(
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank String senha,
    @NotBlank String cpf,
    String endereco,
    String profissao,
    List<RendimentoDTO> rendimentos
) {}