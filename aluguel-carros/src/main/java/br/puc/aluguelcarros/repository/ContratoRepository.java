package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Contrato;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}