package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.model.Funcionario;

public class FuncionarioRepository extends RepositorioBase<Funcionario> {

    public FuncionarioRepository() {
        super(Funcionario.class);
    }

    public Funcionario buscarPorCpf(String cpf) {
        return consultarUm("SELECT f FROM Funcionario f WHERE f.cpf = ?1", Funcionario.class, cpf);
    }
}
