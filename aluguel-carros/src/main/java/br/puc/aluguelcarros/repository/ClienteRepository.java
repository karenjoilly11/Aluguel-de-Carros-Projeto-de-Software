package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Cliente;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca um cliente pelo CPF.
     */
    Optional<Cliente> findByCpf(String cpf);

    /**
     * Verifica se o CPF já existe para evitar duplicidade.
     */
    boolean existsByCpf(String cpf);

    /**
     * Busca cliente por ID carregando a lista de rendimentos.
     * Removido o JOIN FETCH de entidadeEmpregadora, pois agora ela é um atributo String
     * dentro da classe Rendimento conforme o diagrama.
     */
    @Query("SELECT DISTINCT c FROM Cliente c " +
           "LEFT JOIN FETCH c.rendimentos r " +
           "WHERE c.id = :id")
    Optional<Cliente> findByIdWithDetails(Long id);

    /**
     * Lista todos os clientes trazendo seus rendimentos de forma ávida (Eager).
     */
    @Query("SELECT DISTINCT c FROM Cliente c " +
           "LEFT JOIN FETCH c.rendimentos r")
    List<Cliente> findAllWithDetails();
}