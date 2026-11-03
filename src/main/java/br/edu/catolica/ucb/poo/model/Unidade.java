package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "unidades", uniqueConstraints = @UniqueConstraint(columnNames = {"bloco", "numero"}))
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bloco", nullable = false, length = 10)
    private String bloco;

    @Column(name = "numero", nullable = false, length = 10)
    private String numero;

    public Unidade() {
    }

    public Unidade(String bloco, String numero) {
        this.bloco = bloco;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdentificacao() {
        return "Bloco " + bloco + " - Apto " + numero;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s", id, getIdentificacao());
    }
}
