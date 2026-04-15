package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Empresa;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}