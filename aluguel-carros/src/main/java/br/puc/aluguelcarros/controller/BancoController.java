package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.BancoRequestDTO;
import br.puc.aluguelcarros.dto.BancoResponseDTO;
import br.puc.aluguelcarros.model.Banco;
import br.puc.aluguelcarros.service.BancoService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/bancos")
public class BancoController {

    private final BancoService service;

    public BancoController(BancoService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public BancoResponseDTO criar(@Body BancoRequestDTO dto) {
        Banco banco = new Banco();
        banco.setCpf(dto.cnpj()); 
        banco.setEndereco(dto.endereco());
        banco.setProfissao(dto.profissao());

        Banco salvo = service.salvar(banco);
        return new BancoResponseDTO(salvo.getId(), salvo.getCpf(), salvo.getEndereco(), salvo.getProfissao());
    }

    @Get
    public List<BancoResponseDTO> listar() {
        return service.listarTodos().stream()
            .map(b -> new BancoResponseDTO(b.getId(), b.getCpf(), b.getEndereco(), b.getProfissao()))
            .collect(Collectors.toList());
    }
}