package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Agente;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
}