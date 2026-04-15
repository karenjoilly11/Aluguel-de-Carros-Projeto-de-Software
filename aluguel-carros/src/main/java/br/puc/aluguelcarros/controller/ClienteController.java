package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.ClienteRequestDTO;
import br.puc.aluguelcarros.dto.ClienteResponseDTO;
import br.puc.aluguelcarros.service.ClienteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@Controller("/clientes")
// Se ainda não configurou o login, use SecurityRule.IS_ANONYMOUS ou remova a linha abaixo
@Secured(SecurityRule.IS_ANONYMOUS) 
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ── CREATE ────────────────────────────────────────────────────────
    @Post
    public HttpResponse<ClienteResponseDTO> criar(@Body @Valid ClienteRequestDTO dto) {
        try {
            ClienteResponseDTO criado = clienteService.cadastrarCliente(dto);
            return HttpResponse.created(criado);
        } catch (IllegalArgumentException e) {
            // Retorna 400 Bad Request com a mensagem de erro da validação (ex: CPF já existe)
            return HttpResponse.badRequest(); 
        }
    }

    // ── READ ALL ──────────────────────────────────────────────────────
    @Get
    public HttpResponse<List<ClienteResponseDTO>> listarTodos() {
        return HttpResponse.ok(clienteService.listarTodos());
    }

    // ── READ BY ID ────────────────────────────────────────────────────
    @Get("/{id}")
    public HttpResponse<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            return HttpResponse.ok(clienteService.buscarPorId(id));
        } catch (NoSuchElementException e) {
            return HttpResponse.notFound();
        }
    }

    // ── UPDATE ────────────────────────────────────────────────────────
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
    @Delete("/{id}")
    public HttpResponse<Void> excluir(@PathVariable Long id) {
        try {
            clienteService.excluirCadastro(id);
            return HttpResponse.noContent();
        } catch (NoSuchElementException e) {
            return HttpResponse.notFound();
        }
    }
}