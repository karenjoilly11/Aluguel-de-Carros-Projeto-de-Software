package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Banco;
import br.puc.aluguelcarros.repository.BancoRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;

@Singleton
public class BancoService {

    private final BancoRepository repository;

    public BancoService(BancoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Banco salvar(Banco banco) {
        return repository.save(banco);
    }

    public List<Banco> listarTodos() {
        return repository.findAll();
    }

    // Métodos do Diagrama (Especialização do Agente)
    public boolean realizarAnaliseFinanceira(Long idPedido) {
        // Lógica: Consultar restrições de crédito para o aluguel
        return true;
    }

    public void avaliarPedido(Long idPedido, String parecer) {
        // Lógica de avaliação bancária
    }
}