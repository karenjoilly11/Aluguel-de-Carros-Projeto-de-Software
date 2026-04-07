package br.puc.aluguelcarros.repository;

import br.puc.aluguelcarros.model.Cliente;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

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
}
