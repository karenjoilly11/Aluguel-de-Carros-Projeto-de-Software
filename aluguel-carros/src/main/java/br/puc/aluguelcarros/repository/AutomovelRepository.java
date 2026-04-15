package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Automovel;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, String> {
    
    @Override
    List<Automovel> findAll();
}