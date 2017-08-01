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
import model.Tipopista;

public class TipopistaDAO implements CrudDAO<Tipopista> {

    private static TipopistaDAO instance = new TipopistaDAO();

    public static TipopistaDAO getInstance() {
        return instance;
    }

    public TipopistaDAO() {
    }

    @Override
    public void salvar(Tipopista tipopista) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(tipopista);
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
    public void alterar(Tipopista tipopista) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tipopista);
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

    public Tipopista getTipopista(Integer id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        Tipopista tipopista = null;
        try {
            tx.begin();
            tipopista = em.find(Tipopista.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
        return tipopista;
    }

    @Override
    public void excluir(Tipopista tipopista) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.getReference(Tipopista.class, tipopista.getIdTipopista()));
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
    public List<Tipopista> buscar() {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Tipopista> tiposPista = null;
        try {
            tx.begin();
            TypedQuery<Tipopista> query = em.createQuery("select t from Tipopista t", Tipopista.class);
            tiposPista = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
        return tiposPista;
    }

}
