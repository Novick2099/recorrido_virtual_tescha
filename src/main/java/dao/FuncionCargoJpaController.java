/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Cargo;
import entidades.FuncionCargo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author MarzoNegro
 */
public class FuncionCargoJpaController implements Serializable {

    public FuncionCargoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FuncionCargo funcionCargo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cargo fkCargo = funcionCargo.getFkCargo();
            if (fkCargo != null) {
                fkCargo = em.getReference(fkCargo.getClass(), fkCargo.getIdCargo());
                funcionCargo.setFkCargo(fkCargo);
            }
            em.persist(funcionCargo);
            if (fkCargo != null) {
                fkCargo.getFuncionCargoCollection().add(funcionCargo);
                fkCargo = em.merge(fkCargo);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FuncionCargo funcionCargo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FuncionCargo persistentFuncionCargo = em.find(FuncionCargo.class, funcionCargo.getIdFuncion());
            Cargo fkCargoOld = persistentFuncionCargo.getFkCargo();
            Cargo fkCargoNew = funcionCargo.getFkCargo();
            if (fkCargoNew != null) {
                fkCargoNew = em.getReference(fkCargoNew.getClass(), fkCargoNew.getIdCargo());
                funcionCargo.setFkCargo(fkCargoNew);
            }
            funcionCargo = em.merge(funcionCargo);
            if (fkCargoOld != null && !fkCargoOld.equals(fkCargoNew)) {
                fkCargoOld.getFuncionCargoCollection().remove(funcionCargo);
                fkCargoOld = em.merge(fkCargoOld);
            }
            if (fkCargoNew != null && !fkCargoNew.equals(fkCargoOld)) {
                fkCargoNew.getFuncionCargoCollection().add(funcionCargo);
                fkCargoNew = em.merge(fkCargoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionCargo.getIdFuncion();
                if (findFuncionCargo(id) == null) {
                    throw new NonexistentEntityException("The funcionCargo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FuncionCargo funcionCargo;
            try {
                funcionCargo = em.getReference(FuncionCargo.class, id);
                funcionCargo.getIdFuncion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionCargo with id " + id + " no longer exists.", enfe);
            }
            Cargo fkCargo = funcionCargo.getFkCargo();
            if (fkCargo != null) {
                fkCargo.getFuncionCargoCollection().remove(funcionCargo);
                fkCargo = em.merge(fkCargo);
            }
            em.remove(funcionCargo);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FuncionCargo> findFuncionCargoEntities() {
        return findFuncionCargoEntities(true, -1, -1);
    }

    public List<FuncionCargo> findFuncionCargoEntities(int maxResults, int firstResult) {
        return findFuncionCargoEntities(false, maxResults, firstResult);
    }

    private List<FuncionCargo> findFuncionCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FuncionCargo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FuncionCargo findFuncionCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FuncionCargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FuncionCargo> rt = cq.from(FuncionCargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
