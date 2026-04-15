package br.puc.aluguelcarros.service;

import br.puc.aluguelcarros.model.Automovel;
import br.puc.aluguelcarros.repository.AutomovelRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class AutomovelService {

    private final AutomovelRepository repository;

    public AutomovelService(AutomovelRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva ou atualiza um automóvel no banco de dados.
     * O uso de @Transactional garante que a operação seja atômica.
     */
    @Transactional
    public Automovel salvar(Automovel automovel) {
        // Regra de negócio simples: garantir que a placa esteja sempre em maiúsculas
        if (automovel.getPlaca() != null) {
            automovel.setPlaca(automovel.getPlaca().toUpperCase());
        }
        return repository.save(automovel);
    }

    /**
     * Retorna todos os veículos cadastrados.
     */
    public List<Automovel> listarTodos() {
        return repository.findAll();
    }

    /**
     * Busca um veículo específico pela matrícula (ID).
     */
    public Optional<Automovel> buscarPorMatricula(String matricula) {
        return repository.findById(matricula);
    }

    /**
     * Implementação do método verificarDisponibilidade() do Diagrama de Classes.
     */
    public boolean verificarDisponibilidade(String matricula) {
        return repository.findById(matricula)
                .map(Automovel::isDisponivel)
                .orElse(false);
    }

    /**
     * Remove um veículo do sistema.
     */
    @Transactional
    public void excluir(String matricula) {
        repository.deleteById(matricula);
    }

    /**
     * Atualiza apenas o status de disponibilidade (útil após um PedidoAluguel ser aprovado).
     */
    @Transactional
    public void atualizarStatusDisponibilidade(String matricula, boolean status) {
        repository.findById(matricula).ifPresent(carro -> {
            carro.setDisponivel(status);
            repository.update(carro);
        });
    }
}