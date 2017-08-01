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
import model.Desempenho;

public class DesempenhoDAO implements CrudDAO<Desempenho> {

    private static DesempenhoDAO instance = new DesempenhoDAO();

    public static DesempenhoDAO getInstance() {
        return instance;
    }

    public DesempenhoDAO() {
    }

    @Override
    public void salvar(Desempenho desempenho) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(desempenho);
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
    public void alterar(Desempenho desempenho) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(desempenho);
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

    public Desempenho getDesempenho(Integer id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        Desempenho desempenho = null;
        try {
            tx.begin();
            desempenho = em.find(Desempenho.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
        return desempenho;
    }

    @Override
    public void excluir(Desempenho desempenho) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.getReference(Desempenho.class, desempenho.getIdDesempenho()));
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
    public List<Desempenho> buscar() {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Desempenho> desempenhos = null;
        try {
            tx.begin();
            TypedQuery<Desempenho> query = em.createQuery("select d from Desempenho d", Desempenho.class);
            desempenhos = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            PersistenceUtil.close(em);
        }
        return desempenhos;
    }
}
