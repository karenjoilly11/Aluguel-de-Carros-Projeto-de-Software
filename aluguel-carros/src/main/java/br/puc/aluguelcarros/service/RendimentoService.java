package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Rendimento;
import br.puc.aluguelcarros.repository.RendimentoRepository;
import br.puc.aluguelcarros.repository.ClienteRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;

@Singleton
public class RendimentoService {

    private final RendimentoRepository repository;
    private final ClienteRepository clienteRepository;

    public RendimentoService(RendimentoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Rendimento salvar(Long clienteId, Rendimento rendimento) {
        return clienteRepository.findById(clienteId).map(cliente -> {
            // Regra de negócio: máximo 3 rendimentos
            if (cliente.getRendimentos().size() >= 3) {
                throw new IllegalStateException("Cliente já possui o máximo de 3 rendimentos.");
            }
            rendimento.setCliente(cliente);
            return repository.save(rendimento);
        }).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public List<Rendimento> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}