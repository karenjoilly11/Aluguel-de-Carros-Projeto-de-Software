package br.puc.aluguelcarros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import io.micronaut.core.annotation.Introspected;

@Introspected
@Entity
@Table(name = "automoveis")
public class Automovel {
    
    @Id
    private String matricula;
    
    private int ano;
    private String marca;
    private String modelo;
    private String placa;
    private boolean disponivel;

    // Construtores
    public Automovel() {}

    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public int getAno() { return ano; }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}