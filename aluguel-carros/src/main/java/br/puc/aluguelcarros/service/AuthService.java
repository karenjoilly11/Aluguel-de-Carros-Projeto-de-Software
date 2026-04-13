package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Usuario;
import br.puc.aluguelcarros.repository.UsuarioRepository;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

/**
 * Serviço de autenticação.
 * - Valida credenciais contra o banco usando BCrypt
 * - Gera JWT via JwtTokenGenerator do Micronaut Security
 * - Cria o usuário admin padrão se o banco estiver vazio
 */
@Singleton
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtTokenGenerator tokenGenerator;

    public AuthService(UsuarioRepository usuarioRepository,
                       JwtTokenGenerator tokenGenerator) {
        this.usuarioRepository = usuarioRepository;
        this.tokenGenerator    = tokenGenerator;
        criarAdminSeNaoExistir();
    }

    /**
     * Cria um admin padrão na primeira vez que o sistema sobe.
     * Credenciais: admin@driveelite.com / admin123
     */
    private void criarAdminSeNaoExistir() {
        if (usuarioRepository.findByEmail("admin@driveelite.com").isEmpty()) {
            String hash = BCrypt.hashpw("admin123", BCrypt.gensalt());
            usuarioRepository.save(new Usuario("admin@driveelite.com", hash, "Administrador", "ADMIN"));
        }
    }

    /**
     * Autentica o usuário e retorna o JWT + dados do perfil.
     * Lança IllegalArgumentException em caso de credencial inválida.
     */
    public Map<String, Object> login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));

        if (!BCrypt.checkpw(senha, usuario.getSenhaHash())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        // Claims do JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", usuario.getEmail());
        claims.put("nome", usuario.getNome());
        claims.put("role", usuario.getRole());
        claims.put("id", usuario.getId());

        String token = tokenGenerator.generateToken(claims)
                .orElseThrow(() -> new RuntimeException("Erro ao gerar token"));

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("nome",  usuario.getNome());
        result.put("email", usuario.getEmail());
        result.put("role",  usuario.getRole());
        return result;
    }
}
