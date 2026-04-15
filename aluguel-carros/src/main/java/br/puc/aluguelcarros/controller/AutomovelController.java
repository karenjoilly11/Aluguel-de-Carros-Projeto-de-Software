package br.puc.aluguelcarros.controller;

import br.puc.aluguelcarros.dto.AutomovelRequestDTO;
import br.puc.aluguelcarros.dto.AutomovelResponseDTO;
import br.puc.aluguelcarros.model.Automovel;
import br.puc.aluguelcarros.service.AutomovelService;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/automoveis")
public class AutomovelController {

    private final AutomovelService service;

    public AutomovelController(AutomovelService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public AutomovelResponseDTO cadastrar(@Body AutomovelRequestDTO dto) {
        // Converte DTO para Entidade
        Automovel novoCarro = new Automovel();
        novoCarro.setMatricula(dto.matricula());
        novoCarro.setMarca(dto.marca());
        novoCarro.setModelo(dto.modelo());
        novoCarro.setAno(dto.ano());
        novoCarro.setPlaca(dto.placa());
        novoCarro.setDisponivel(dto.disponivel());

        Automovel salvo = service.salvar(novoCarro);

        // Retorna o ResponseDTO
        return mapperToResponse(salvo);
    }

    @Get
    public List<AutomovelResponseDTO> listar() {
        return service.listarTodos().stream()
                .map(this::mapperToResponse)
                .collect(Collectors.toList());
    }

    // Helper method para mapeamento
    private AutomovelResponseDTO mapperToResponse(Automovel carro) {
        return new AutomovelResponseDTO(
            carro.getMatricula(),
            carro.getMarca(),
            carro.getModelo(),
            carro.getAno(),
            carro.getPlaca(),
            carro.isDisponivel()
        );
    }
}