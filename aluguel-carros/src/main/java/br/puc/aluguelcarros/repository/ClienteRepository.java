package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Cliente;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de acesso a dados do Cliente.
 *
 * DIFERENÇA vs Spring Boot:
 *   Spring Boot → interface extends JpaRepository<T,ID>
 *                 anotada com @Repository (opcional, Spring detecta automaticamente)
 *
 *   Micronaut   → interface extends JpaRepository<T,ID>
 *                 anotada com @Repository (io.micronaut.data.annotation.Repository)
 *                 O Micronaut gera a implementação em TEMPO DE COMPILAÇÃO via
 *                 annotation processor (micronaut-data-processor), sem proxy dinâmico.
 *
 * Métodos herdados de JpaRepository:
 *   save(), findById(), findAll(), update(), delete(), deleteById(), count()
 *
 * Métodos derivados de query seguem a mesma convenção do Spring Data:
 *   findBy<Campo>() → gera automaticamente a query JPQL no build time.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca um cliente pelo CPF.
     * Gerado automaticamente: SELECT c FROM Cliente c WHERE c.cpf = :cpf
     */
    Optional<Cliente> findByCpf(String cpf);

    /**
     * Verifica unicidade de CPF (útil na validação de cadastro).
     * Gerado automaticamente: SELECT COUNT(c) > 0 FROM Cliente c WHERE c.cpf = :cpf
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica unicidade de RG.
     */
    boolean existsByRg(String rg);

    /**
     * Busca cliente por ID fazendo JOIN FETCH nos rendimentos e suas empregadoras.
     * Apenas uma coleção por JOIN FETCH para evitar MultipleBagFetchException.
     * As entidadesEmpregadoras são carregadas via segundo JOIN FETCH em query separada
     * ou acessadas pela referência já presente nos rendimentos.
     */
    @Query("SELECT DISTINCT c FROM Cliente c " +
           "LEFT JOIN FETCH c.rendimentos r " +
           "LEFT JOIN FETCH r.entidadeEmpregadora " +
           "WHERE c.id = :id")
    Optional<Cliente> findByIdWithDetails(Long id);

    /**
     * Busca todos os clientes com JOIN FETCH nos rendimentos.
     * Apenas uma coleção por JOIN FETCH para evitar MultipleBagFetchException.
     */
    @Query("SELECT DISTINCT c FROM Cliente c " +
           "LEFT JOIN FETCH c.rendimentos r " +
           "LEFT JOIN FETCH r.entidadeEmpregadora")
    List<Cliente> findAllWithDetails();
}
