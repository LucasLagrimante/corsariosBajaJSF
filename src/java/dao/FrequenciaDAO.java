/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import model.Frequencia;

public class FrequenciaDAO implements CrudDAO<Frequencia> {

    private static FrequenciaDAO instance = new FrequenciaDAO();

    public static FrequenciaDAO getInstance() {
        return instance;
    }

    public FrequenciaDAO() {
    }

    @Override
    public void salvar(Frequencia frequencia) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(frequencia);
            tx.commit();
        } catch (RollbackException e) {
            throw new RollbackException("Para preservar a integridade do banco de dados, não foi possivel executar a ação tomada!!", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Reinicie seu servidor!", e);
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
    }

    @Override
    public void alterar(Frequencia frequencia) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(frequencia);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
    }

    public Frequencia getFrequencia(Integer id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        Frequencia frequencia = null;
        try {
            tx.begin();
            frequencia = em.find(Frequencia.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
        return frequencia;
    }

    @Override
    public void excluir(Frequencia frequencia) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.getReference(Frequencia.class, frequencia.getIdFrequencia()));
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
    }

    @Override
    public List<Frequencia> buscar() {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Frequencia> frequencias = null;
        try {
            tx.begin();
            TypedQuery<Frequencia> query = em.createQuery("select f from Frequencia f", Frequencia.class);
            frequencias = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
        return frequencias;
    }

}
