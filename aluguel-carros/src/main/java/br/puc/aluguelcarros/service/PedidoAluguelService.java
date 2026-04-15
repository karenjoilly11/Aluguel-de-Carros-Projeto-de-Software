package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.PedidoAluguel;
import br.puc.aluguelcarros.model.StatusPedido;
import br.puc.aluguelcarros.repository.PedidoAluguelRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class PedidoAluguelService {

    private final PedidoAluguelRepository repository;

    public PedidoAluguelService(PedidoAluguelRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PedidoAluguel criarPedido(PedidoAluguel pedido) {
        // 1. Validação de Regra de Negócio (conforme seu diagrama)
        if (!pedido.validarDatas()) {
            throw new IllegalArgumentException("A data de início deve ser anterior à data de fim.");
        }

        // 2. Inicialização de campos automáticos
        pedido.setDataSolicitacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.SOLICITADO);

        // 3. Persistência via JPA
        return repository.save(pedido);
    }

    public List<PedidoAluguel> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public PedidoAluguel atualizarStatus(Long id, StatusPedido novoStatus) {
        return repository.findById(id).map(pedido -> {
            pedido.atualizarStatus(novoStatus);
            return repository.update(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }

    public Double calcularOrcamento(PedidoAluguel pedido) {
        // Implementa o método calcularValorTotal() do diagrama
        return pedido.calcularValorTotal();
    }
}