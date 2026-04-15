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

    @Transactional
    public Automovel salvar(Automovel automovel) {
        return repository.save(automovel);
    }

    public List<Automovel> listarTodos() {
        return repository.findAll();
    }

    public Optional<Automovel> buscarPorMatricula(String matricula) {
        return repository.findById(matricula);
    }
}