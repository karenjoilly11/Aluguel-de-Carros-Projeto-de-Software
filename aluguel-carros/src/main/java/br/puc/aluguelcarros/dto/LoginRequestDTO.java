package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

@Serdeable
public class LoginRequestDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getSenha() { return senha; }
    public void setSenha(String s) { this.senha = s; }
}
