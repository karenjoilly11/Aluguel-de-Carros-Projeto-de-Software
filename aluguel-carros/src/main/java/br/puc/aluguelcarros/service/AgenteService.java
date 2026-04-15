package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Agente;
import br.puc.aluguelcarros.model.StatusPedido;
import br.puc.aluguelcarros.repository.AgenteRepository;
import br.puc.aluguelcarros.repository.PedidoAluguelRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Singleton
public class AgenteService {

    private final AgenteRepository repository;
    private final PedidoAluguelRepository pedidoRepository;

    public AgenteService(AgenteRepository repository, PedidoAluguelRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public Agente salvar(Agente agente) {
        agente.setRole("AGENTE");
        return repository.save(agente);
    }

    public List<Agente> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public void avaliarPedido(Long idPedido, String parecer) {
        var pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));

        System.out.println("Agente avaliando pedido " + idPedido + ": " + parecer);

        if (parecer.toUpperCase().contains("APROVADO")) {
            pedido.setStatus(StatusPedido.APROVADO_PELA_EMPRESA); // Ou um status específico de AGENTE
        } else {
            pedido.setStatus(StatusPedido.REPROVADO_PELA_EMPRESA);
        }
        
        pedidoRepository.update(pedido);
    }
}