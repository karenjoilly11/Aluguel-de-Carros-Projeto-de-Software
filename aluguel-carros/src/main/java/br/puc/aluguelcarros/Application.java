package br.puc.aluguelcarros;

import io.micronaut.runtime.Micronaut;

/**
 * Ponto de entrada da aplicação Micronaut.
 *
 * DIFERENÇA vs Spring Boot:
 *   Spring Boot → SpringApplication.run(App.class, args)
 *   Micronaut   → Micronaut.run(App.class, args)
 *
 * Não há @SpringBootApplication nem @EnableAutoConfiguration.
 * O Micronaut processa toda injeção de dependência em tempo de
 * compilação (via annotation processors), eliminando o uso de reflexão.
 */
public class Application {

    public static void main(String[] args) {
        Micronaut.build(args)
                .mainClass(Application.class)
                .properties(java.util.Map.of(
                        "micronaut.server.port", "8082",
                        "datasources.default.url", "jdbc:postgresql://localhost:5432/aluguelcarros",
                        "datasources.default.driver-class-name", "org.postgresql.Driver",
                        "datasources.default.username", "postgres",
                        "datasources.default.password", "12345",
                        "datasources.default.dialect", "POSTGRES",
                        "jpa.default.properties.hibernate.hbm2ddl.auto", "update",
                        "jpa.default.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"
                ))
                .start();
    }
}
