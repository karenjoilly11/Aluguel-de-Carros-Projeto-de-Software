package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Banco;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
}