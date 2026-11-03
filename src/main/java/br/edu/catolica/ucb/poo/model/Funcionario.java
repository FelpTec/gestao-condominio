package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "funcionarios")
public class Funcionario extends Pessoa {

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo", nullable = false, length = 20)
    private Cargo cargo;

    @Column(name = "salario", nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    public Funcionario() {
    }

    public Funcionario(String nome, String cpf, String telefone, Cargo cargo, BigDecimal salario) {
        super(nome, cpf, telefone);
        this.cargo = cargo;
        this.salario = salario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s - CPF %s - R$ %.2f", getId(), getNome(), cargo, getCpfFormatado(), salario);
    }
}
