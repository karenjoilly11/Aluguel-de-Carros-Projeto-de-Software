package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.AgenteRequestDTO;
import br.puc.aluguelcarros.dto.AgenteResponseDTO;
import br.puc.aluguelcarros.model.Agente;
import br.puc.aluguelcarros.service.AgenteService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;
import jakarta.validation.Valid;

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
    public AgenteResponseDTO criar(@Body @Valid AgenteRequestDTO dto) {
        Agente agente = new Agente();
        
        // Agora esses métodos existem porque estão no record!
        agente.setNome(dto.nome());
        agente.setEmail(dto.email());
        agente.setSenhaHash(dto.senha()); // Lembre-se: Usuario usa senhaHash
        
        agente.setCpf(dto.cpf());
        agente.setEndereco(dto.endereco());
        agente.setProfissao(dto.profissao());

        Agente salvo = service.salvar(agente);
        return mapperToResponse(salvo);
    }

    @Get
    public List<AgenteResponseDTO> listar() {
        return service.listarTodos().stream()
            .map(this::mapperToResponse)
            .collect(Collectors.toList());
    }

    private AgenteResponseDTO mapperToResponse(Agente a) {
        return new AgenteResponseDTO(
            a.getId(), 
            a.getNome(), 
            a.getEmail(), 
            a.getCpf(), 
            a.getEndereco(), 
            a.getProfissao()
        );
    }
}