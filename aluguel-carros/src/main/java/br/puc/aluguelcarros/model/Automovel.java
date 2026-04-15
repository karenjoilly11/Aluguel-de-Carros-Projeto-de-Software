package br.puc.aluguelcarros.model;

import jakarta.persistence.*;
import io.micronaut.core.annotation.Introspected;

@Introspected
@Entity
@Table(name = "automoveis")
public class Automovel {

    @Id
    private String matricula; // ID Principal

    private String placa;
    private String marca;
    private String modelo;
    private Integer ano;
    private Double valorDiaria;
    private boolean disponivel = true;

    public Automovel() {}

    // --- Getters e Setters (Essenciais para a Controller e Service) ---

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; } // Resolve setMarca

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; } // Resolve setModelo

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; } // Resolve setAno

    public Double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(Double valorDiaria) { this.valorDiaria = valorDiaria; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}