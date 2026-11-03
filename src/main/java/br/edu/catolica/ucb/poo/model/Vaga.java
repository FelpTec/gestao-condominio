package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vagas")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero", nullable = false, unique = true)
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    public Vaga() {
    }

    public Vaga(Integer numero) {
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public boolean estaOcupada() {
        return unidade != null;
    }

    @Override
    public String toString() {
        String situacao = estaOcupada() ? "ocupada por " + unidade.getIdentificacao() : "LIVRE";
        return String.format("[%d] Vaga %02d - %s", id, numero, situacao);
    }
}
