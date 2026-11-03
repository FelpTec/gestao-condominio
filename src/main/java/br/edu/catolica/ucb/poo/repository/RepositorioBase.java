package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public abstract class RepositorioBase<T> {

    private final Class<T> tipo;

    protected RepositorioBase(Class<T> tipo) {
        this.tipo = tipo;
    }

    public List<T> listar() {
        return consultar("SELECT e FROM " + tipo.getSimpleName() + " e ORDER BY e.id", tipo);
    }

    public T buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(tipo, id);
        } finally {
            em.close();
        }
    }

    public T salvar(T entidade) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
            return entidade;
        } catch (RuntimeException e) {
            desfazer(em);
            throw e;
        } finally {
            em.close();
        }
    }

    public T atualizar(T entidade) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T atualizada = em.merge(entidade);
            em.getTransaction().commit();
            return atualizada;
        } catch (RuntimeException e) {
            desfazer(em);
            throw e;
        } finally {
            em.close();
        }
    }

    public void excluir(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T entidade = em.find(tipo, id);
            if (entidade != null) {
                em.remove(entidade);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            desfazer(em);
            throw e;
        } finally {
            em.close();
        }
    }

    protected <R> List<R> consultar(String jpql, Class<R> tipoResultado, Object... parametros) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<R> query = em.createQuery(jpql, tipoResultado);
            aplicar(query, parametros);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    protected <R> R consultarUm(String jpql, Class<R> tipoResultado, Object... parametros) {
        List<R> resultados = consultar(jpql, tipoResultado, parametros);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    protected long contar(String jpql, Object... parametros) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            aplicar(query, parametros);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    private void aplicar(TypedQuery<?> query, Object[] parametros) {
        for (int i = 0; i < parametros.length; i++) {
            query.setParameter(i + 1, parametros[i]);
        }
    }

    private void desfazer(EntityManager em) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
