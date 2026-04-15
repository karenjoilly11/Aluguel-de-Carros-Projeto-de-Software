package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.EmpresaRequestDTO;
import br.puc.aluguelcarros.dto.EmpresaResponseDTO;
import br.puc.aluguelcarros.model.Empresa;
import br.puc.aluguelcarros.service.EmpresaService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/empresas")
public class EmpresaController {

    private final EmpresaService service;

    public EmpresaController(EmpresaService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public EmpresaResponseDTO criar(@Body EmpresaRequestDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setCpf(dto.cnpj()); // Mapeando CNPJ para o campo CPF da base
        empresa.setEndereco(dto.endereco());
        empresa.setProfissao(dto.profissao());

        Empresa salva = service.salvar(empresa);
        return new EmpresaResponseDTO(salva.getId(), salva.getCpf(), salva.getEndereco(), salva.getProfissao());
    }

    @Get
    public List<EmpresaResponseDTO> listar() {
        return service.listarTodas().stream()
            .map(e -> new EmpresaResponseDTO(e.getId(), e.getCpf(), e.getEndereco(), e.getProfissao()))
            .collect(Collectors.toList());
    }
}