package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.PedidoAluguel;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface PedidoAluguelRepository extends JpaRepository<PedidoAluguel, Long> {
}