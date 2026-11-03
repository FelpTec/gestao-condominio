package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "moradores")
public class Morador extends Pessoa {

    @ManyToOne(optional = false)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @Column(name = "proprietario", nullable = false)
    private boolean proprietario;

    public Morador() {
    }

    public Morador(String nome, String cpf, String telefone, boolean proprietario, Unidade unidade) {
        super(nome, cpf, telefone);
        this.proprietario = proprietario;
        this.unidade = unidade;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public boolean isProprietario() {
        return proprietario;
    }

    public void setProprietario(boolean proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - CPF %s - %s (%s)", getId(), getNome(), getCpfFormatado(),
                unidade.getIdentificacao(), proprietario ? "proprietario" : "inquilino");
    }
}
