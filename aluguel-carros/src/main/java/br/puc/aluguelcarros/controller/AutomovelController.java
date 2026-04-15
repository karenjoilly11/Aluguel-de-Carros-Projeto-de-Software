package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.model.Automovel;
import br.puc.aluguelcarros.service.AutomovelService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;
import java.util.List;

@Controller("/automoveis")
public class AutomovelController {

    private final AutomovelService service;

    public AutomovelController(AutomovelService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public Automovel cadastrar(@Body Automovel automovel) {
        return service.salvar(automovel);
    }

    @Get
    public List<Automovel> listar() {
        return service.listarTodos();
    }
}