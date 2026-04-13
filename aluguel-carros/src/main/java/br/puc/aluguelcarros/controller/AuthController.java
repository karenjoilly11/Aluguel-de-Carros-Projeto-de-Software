package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.LoginRequestDTO;
import br.puc.aluguelcarros.service.AuthService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;

import java.util.Map;

/**
 * Controller de autenticação.
 *
 * POST /auth/login  → aberto, retorna JWT
 * GET  /auth/me     → protegido, retorna dados do usuário logado
 */
@Controller("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login — rota pública.
     * Recebe email + senha, valida e retorna JWT.
     */
    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> login(@Body @Valid LoginRequestDTO dto) {
        try {
            Map<String, Object> result = authService.login(dto.getEmail(), dto.getSenha());
            return HttpResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Email ou senha inválidos"));
        }
    }

    /**
     * Me — rota protegida.
     * Valida o JWT e retorna os dados do usuário autenticado.
     */
    @Get("/me")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<?> me(Authentication authentication) {
        return HttpResponse.ok(Map.of(
                "email", authentication.getName(),
                "nome",  authentication.getAttributes().getOrDefault("nome", ""),
                "role",  authentication.getAttributes().getOrDefault("role", "")
        ));
    }
}
