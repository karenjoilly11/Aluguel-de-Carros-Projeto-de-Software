package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.ContratoRequestDTO;
import br.puc.aluguelcarros.dto.ContratoResponseDTO;
import br.puc.aluguelcarros.model.Contrato;
import br.puc.aluguelcarros.service.ContratoService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;

@Controller("/contratos")
public class ContratoController {

    private final ContratoService service;

    public ContratoController(ContratoService service) {
        this.service = service;
    }

   @Post
@Status(HttpStatus.CREATED)
public ContratoResponseDTO criar(@Body ContratoRequestDTO dto) {
    Contrato c = service.gerarContrato(dto.pedidoId(), dto.termos(), dto.valorFinal(), dto.bancoId());
    
    return new ContratoResponseDTO(
        c.getId(), 
        c.getTermos(), 
        c.getValorFinal(), 
        c.isAssinado(), 
        c.isPossuiCredito(), // Adicionado na Model
        c.getPedidoAluguel() != null ? c.getPedidoAluguel().getId() : null
    );
}

    @Patch("/{id}/assinar")
    public void assinar(@PathVariable Long id) {
        service.assinar(id);
    }
}