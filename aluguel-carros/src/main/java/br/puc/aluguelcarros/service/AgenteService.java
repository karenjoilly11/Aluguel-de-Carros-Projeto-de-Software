package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Agente;
import br.puc.aluguelcarros.repository.AgenteRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;

@Singleton
public class AgenteService {

    private final AgenteRepository repository;

    public AgenteService(AgenteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Agente salvar(Agente agente) {
        return repository.save(agente);
    }

    public List<Agente> listarTodos() {
        return repository.findAll();
    }

    // Base para lógica do diagrama: avaliarPedido
    public void avaliarPedido(Long idPedido, String parecer) {
        // Lógica de avaliação será implementada conforme a regra de negócio
        System.out.println("Agente avaliando pedido " + idPedido + " com parecer: " + parecer);
    }
}