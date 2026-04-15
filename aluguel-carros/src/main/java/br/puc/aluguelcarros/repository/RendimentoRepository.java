package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Rendimento;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface RendimentoRepository extends JpaRepository<Rendimento, Long> {
    // Busca todos os rendimentos de um cliente específico
    List<Rendimento> findByClienteId(Long clienteId);
}