package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.model.Morador;

import java.util.List;

public class MoradorRepository extends RepositorioBase<Morador> {

    public MoradorRepository() {
        super(Morador.class);
    }

    @Override
    public List<Morador> listar() {
        return consultar("SELECT m FROM Morador m JOIN FETCH m.unidade ORDER BY m.id", Morador.class);
    }

    public Morador buscarPorCpf(String cpf) {
        return consultarUm("SELECT m FROM Morador m JOIN FETCH m.unidade WHERE m.cpf = ?1", Morador.class, cpf);
    }

    public long contarPorUnidade(Integer unidadeId) {
        return contar("SELECT COUNT(m) FROM Morador m WHERE m.unidade.id = ?1", unidadeId);
    }

    public long contarProprietarios() {
        return contar("SELECT COUNT(m) FROM Morador m WHERE m.proprietario = true");
    }
}
