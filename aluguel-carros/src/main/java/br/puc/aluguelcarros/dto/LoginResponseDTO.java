package br.puc.aluguelcarros.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class LoginResponseDTO {

    private String token;
    private String nome;
    private String email;
    private String role;

    public LoginResponseDTO(String token, String nome, String email, String role) {
        this.token = token;
        this.nome  = nome;
        this.email = email;
        this.role  = role;
    }

    public String getToken() { return token; }
    public String getNome()  { return nome; }
    public String getEmail() { return email; }
    public String getRole()  { return role; }
}
