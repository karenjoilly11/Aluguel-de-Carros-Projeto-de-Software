package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.RendimentoDTO;
import br.puc.aluguelcarros.model.Rendimento;
import br.puc.aluguelcarros.service.RendimentoService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/clientes/{clienteId}/rendimentos")
public class RendimentoController {

    private final RendimentoService service;

    public RendimentoController(RendimentoService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public RendimentoDTO adicionar(@PathVariable Long clienteId, @Body RendimentoDTO dto) {
        Rendimento rend = new Rendimento();
        rend.setValor(dto.valor());
        rend.setEntidadeEmpregadora(dto.nomeEntidadeEmpregadora());
        
        Rendimento salvo = service.salvar(clienteId, rend);
        return new RendimentoDTO(salvo.getId(), salvo.getValor(), salvo.getEntidadeEmpregadora());
    }

    @Get
    public List<RendimentoDTO> listar(@PathVariable Long clienteId) {
        return service.listarPorCliente(clienteId).stream()
                .map(r -> new RendimentoDTO(r.getId(), r.getValor(), r.getEntidadeEmpregadora()))
                .collect(Collectors.toList());
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }
}