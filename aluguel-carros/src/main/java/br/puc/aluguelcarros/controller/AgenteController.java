package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.AgenteRequestDTO;
import br.puc.aluguelcarros.dto.AgenteResponseDTO;
import br.puc.aluguelcarros.model.Agente;
import br.puc.aluguelcarros.service.AgenteService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/agentes")
public class AgenteController {

    private final AgenteService service;

    public AgenteController(AgenteService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public AgenteResponseDTO criar(@Body AgenteRequestDTO dto) {
        Agente agente = new Agente();
        agente.setCpf(dto.cpf());
        agente.setEndereco(dto.endereco());
        agente.setProfissao(dto.profissao());

        Agente salvo = service.salvar(agente);
        return new AgenteResponseDTO(salvo.getId(), salvo.getCpf(), salvo.getEndereco(), salvo.getProfissao());
    }

    @Get
    public List<AgenteResponseDTO> listar() {
        return service.listarTodos().stream()
            .map(a -> new AgenteResponseDTO(a.getId(), a.getCpf(), a.getEndereco(), a.getProfissao()))
            .collect(Collectors.toList());
    }
}