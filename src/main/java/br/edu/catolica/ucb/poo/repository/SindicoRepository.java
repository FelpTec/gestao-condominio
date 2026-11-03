package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.model.Sindico;

import java.util.List;

public class SindicoRepository extends RepositorioBase<Sindico> {

    public SindicoRepository() {
        super(Sindico.class);
    }

    @Override
    public List<Sindico> listar() {
        return consultar("SELECT s FROM Sindico s JOIN FETCH s.morador m JOIN FETCH m.unidade ORDER BY s.id DESC",
                Sindico.class);
    }

    public Sindico buscarAtivo() {
        return consultarUm("SELECT s FROM Sindico s JOIN FETCH s.morador m JOIN FETCH m.unidade WHERE s.ativo = true",
                Sindico.class);
    }

    public long contarPorMorador(Integer moradorId) {
        return contar("SELECT COUNT(s) FROM Sindico s WHERE s.morador.id = ?1", moradorId);
    }
}
