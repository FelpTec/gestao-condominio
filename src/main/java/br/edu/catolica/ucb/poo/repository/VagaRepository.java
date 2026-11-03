package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.model.Vaga;
import br.edu.catolica.ucb.poo.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class VagaRepository extends RepositorioBase<Vaga> {

    public VagaRepository() {
        super(Vaga.class);
    }

    @Override
    public List<Vaga> listar() {
        return consultar("SELECT v FROM Vaga v LEFT JOIN FETCH v.unidade ORDER BY v.numero", Vaga.class);
    }

    public Vaga buscarPorNumero(Integer numero) {
        return consultarUm("SELECT v FROM Vaga v LEFT JOIN FETCH v.unidade WHERE v.numero = ?1", Vaga.class, numero);
    }

    public long contarTotal() {
        return contar("SELECT COUNT(v) FROM Vaga v");
    }

    public long contarOcupadas() {
        return contar("SELECT COUNT(v) FROM Vaga v WHERE v.unidade IS NOT NULL");
    }

    public long contarPorUnidade(Integer unidadeId) {
        return contar("SELECT COUNT(v) FROM Vaga v WHERE v.unidade.id = ?1", unidadeId);
    }

    public int maiorNumero() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Integer maior = em.createQuery("SELECT MAX(v.numero) FROM Vaga v", Integer.class).getSingleResult();
            return maior == null ? 0 : maior;
        } finally {
            em.close();
        }
    }
}
