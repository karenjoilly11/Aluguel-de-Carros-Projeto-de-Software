package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.ClienteRequestDTO;
import br.puc.aluguelcarros.dto.ClienteResponseDTO;
import br.puc.aluguelcarros.service.ClienteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controlador REST para o CRUD de Cliente.
 *
 * DIFERENÇAS vs Spring Boot — resumo anotado no código:
 *
 * | Spring Boot                    | Micronaut                          |
 * |--------------------------------|------------------------------------|
 * | @RestController                | @Controller (produz JSON por padrão)|
 * | @RequestMapping("/clientes")   | @Controller("/clientes")            |
 * | @GetMapping                    | @Get                                |
 * | @PostMapping                   | @Post                               |
 * | @PutMapping("/{id}")           | @Put("/{id}")                       |
 * | @DeleteMapping("/{id}")        | @Delete("/{id}")                    |
 * | @RequestBody                   | @Body                               |
 * | @PathVariable                  | @PathVariable (mesmo nome)          |
 * | ResponseEntity<T>              | HttpResponse<T>                     |
 * | @Autowired / construtor        | @Inject / construtor (JSR-330)      |
 * | @Valid (org.springframework)   | @Valid (jakarta.validation)         |
 *
 * Tratamento de exceções: feito inline com try/catch para clareza didática.
 * Em produção, extraia para uma classe @Error ou @ServerExceptionHandler.
 */
@Controller("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    // Injeção via construtor (recomendada — sem @Inject necessário aqui)
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ── CREATE ────────────────────────────────────────────────────────
    /**
     * POST /clientes
     * Cadastra um novo cliente com rendimentos e entidades empregadoras.
     *
     * @Body → equivalente ao @RequestBody do Spring Boot.
     * @Valid → aciona a validação do Bean Validation (jakarta.validation).
     *
     * Retorna 201 Created com o recurso criado no corpo.
     */
    @Post
    public HttpResponse<ClienteResponseDTO> criar(@Body @Valid ClienteRequestDTO dto) {
        try {
            ClienteResponseDTO criado = clienteService.cadastrarCliente(dto);
            // HttpResponse.created() → 201 Created (equivale a ResponseEntity.status(201).body(…))
            return HttpResponse.created(criado);
        } catch (IllegalArgumentException e) {
            // 409 Conflict para violação de unicidade (CPF/RG duplicado)
            return HttpResponse.status(HttpStatus.CONFLICT);
        }
    }

    // ── READ ALL ──────────────────────────────────────────────────────
    /**
     * GET /clientes
     * Retorna todos os clientes cadastrados.
     * HttpResponse.ok() → 200 OK.
     */
    @Get
    public HttpResponse<List<ClienteResponseDTO>> listarTodos() {
        return HttpResponse.ok(clienteService.listarTodos());
    }

    // ── READ BY ID ────────────────────────────────────────────────────
    /**
     * GET /clientes/{id}
     *
     * @PathVariable Long id — captura o segmento de URL {id}.
     * Mesmo comportamento do Spring; o pacote é io.micronaut.http.annotation.
     */
    @Get("/{id}")
    public HttpResponse<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            return HttpResponse.ok(clienteService.buscarPorId(id));
        } catch (NoSuchElementException e) {
            return HttpResponse.notFound(); // 404
        }
    }

    // ── UPDATE ────────────────────────────────────────────────────────
    /**
     * PUT /clientes/{id}
     * Atualiza endereço, profissão e rendimentos do cliente.
     */
    @Put("/{id}")
    public HttpResponse<ClienteResponseDTO> atualizar(@PathVariable Long id,
                                                      @Body @Valid ClienteRequestDTO dto) {
        try {
            return HttpResponse.ok(clienteService.atualizarDados(id, dto));
        } catch (NoSuchElementException e) {
            return HttpResponse.notFound();
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest();
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────
    /**
     * DELETE /clientes/{id}
     * Remove o cadastro do cliente.
     * Retorna 204 No Content em caso de sucesso.
     */
    @Delete("/{id}")
    public HttpResponse<Void> excluir(@PathVariable Long id) {
        try {
            clienteService.excluirCadastro(id);
            return HttpResponse.noContent(); // 204
        } catch (NoSuchElementException e) {
            return HttpResponse.notFound();
        }
    }
}
