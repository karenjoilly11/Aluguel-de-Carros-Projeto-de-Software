package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.PedidoRequestDTO;
import br.puc.aluguelcarros.dto.PedidoResponseDTO;
import br.puc.aluguelcarros.model.PedidoAluguel;
import br.puc.aluguelcarros.service.PedidoAluguelService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/pedidos")
public class PedidoAluguelController {

    private final PedidoAluguelService service;

    public PedidoAluguelController(PedidoAluguelService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public PedidoResponseDTO criar(@Body PedidoRequestDTO dto) {
        // Converte DTO para Entidade
        PedidoAluguel pedido = new PedidoAluguel();
        pedido.setDataInicio(dto.dataInicio());
        pedido.setDataFim(dto.dataFim());
        pedido.setValorPrevisto(dto.valorPrevisto());

        PedidoAluguel salvo = service.criarPedido(pedido);

        // Retorna o ResponseDTO
        return new PedidoResponseDTO(
            salvo.getId(),
            salvo.getDataSolicitacao(),
            salvo.getDataInicio(),
            salvo.getDataFim(),
            salvo.getValorPrevisto(),
            salvo.getStatus()
        );
    }

    @Get
    public List<PedidoResponseDTO> listar() {
        return service.listarTodos().stream()
            .map(p -> new PedidoResponseDTO(
                p.getId(), p.getDataSolicitacao(), p.getDataInicio(), 
                p.getDataFim(), p.getValorPrevisto(), p.getStatus()))
            .collect(Collectors.toList());
    }
}