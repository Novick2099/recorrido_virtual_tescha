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
import entidades.Aula;
import entidades.CatAula;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author MarzoNegro
 */
public class CatAulaJpaController implements Serializable {

    public CatAulaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CatAula catAula) throws RollbackFailureException, Exception {
        if (catAula.getAulaCollection() == null) {
            catAula.setAulaCollection(new ArrayList<Aula>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Aula> attachedAulaCollection = new ArrayList<Aula>();
            for (Aula aulaCollectionAulaToAttach : catAula.getAulaCollection()) {
                aulaCollectionAulaToAttach = em.getReference(aulaCollectionAulaToAttach.getClass(), aulaCollectionAulaToAttach.getIdAula());
                attachedAulaCollection.add(aulaCollectionAulaToAttach);
            }
            catAula.setAulaCollection(attachedAulaCollection);
            em.persist(catAula);
            for (Aula aulaCollectionAula : catAula.getAulaCollection()) {
                CatAula oldFkCatAulaOfAulaCollectionAula = aulaCollectionAula.getFkCatAula();
                aulaCollectionAula.setFkCatAula(catAula);
                aulaCollectionAula = em.merge(aulaCollectionAula);
                if (oldFkCatAulaOfAulaCollectionAula != null) {
                    oldFkCatAulaOfAulaCollectionAula.getAulaCollection().remove(aulaCollectionAula);
                    oldFkCatAulaOfAulaCollectionAula = em.merge(oldFkCatAulaOfAulaCollectionAula);
                }
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

    public void edit(CatAula catAula) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CatAula persistentCatAula = em.find(CatAula.class, catAula.getIdCatAula());
            Collection<Aula> aulaCollectionOld = persistentCatAula.getAulaCollection();
            Collection<Aula> aulaCollectionNew = catAula.getAulaCollection();
            Collection<Aula> attachedAulaCollectionNew = new ArrayList<Aula>();
            for (Aula aulaCollectionNewAulaToAttach : aulaCollectionNew) {
                aulaCollectionNewAulaToAttach = em.getReference(aulaCollectionNewAulaToAttach.getClass(), aulaCollectionNewAulaToAttach.getIdAula());
                attachedAulaCollectionNew.add(aulaCollectionNewAulaToAttach);
            }
            aulaCollectionNew = attachedAulaCollectionNew;
            catAula.setAulaCollection(aulaCollectionNew);
            catAula = em.merge(catAula);
            for (Aula aulaCollectionOldAula : aulaCollectionOld) {
                if (!aulaCollectionNew.contains(aulaCollectionOldAula)) {
                    aulaCollectionOldAula.setFkCatAula(null);
                    aulaCollectionOldAula = em.merge(aulaCollectionOldAula);
                }
            }
            for (Aula aulaCollectionNewAula : aulaCollectionNew) {
                if (!aulaCollectionOld.contains(aulaCollectionNewAula)) {
                    CatAula oldFkCatAulaOfAulaCollectionNewAula = aulaCollectionNewAula.getFkCatAula();
                    aulaCollectionNewAula.setFkCatAula(catAula);
                    aulaCollectionNewAula = em.merge(aulaCollectionNewAula);
                    if (oldFkCatAulaOfAulaCollectionNewAula != null && !oldFkCatAulaOfAulaCollectionNewAula.equals(catAula)) {
                        oldFkCatAulaOfAulaCollectionNewAula.getAulaCollection().remove(aulaCollectionNewAula);
                        oldFkCatAulaOfAulaCollectionNewAula = em.merge(oldFkCatAulaOfAulaCollectionNewAula);
                    }
                }
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
                Integer id = catAula.getIdCatAula();
                if (findCatAula(id) == null) {
                    throw new NonexistentEntityException("The catAula with id " + id + " no longer exists.");
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
            CatAula catAula;
            try {
                catAula = em.getReference(CatAula.class, id);
                catAula.getIdCatAula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catAula with id " + id + " no longer exists.", enfe);
            }
            Collection<Aula> aulaCollection = catAula.getAulaCollection();
            for (Aula aulaCollectionAula : aulaCollection) {
                aulaCollectionAula.setFkCatAula(null);
                aulaCollectionAula = em.merge(aulaCollectionAula);
            }
            em.remove(catAula);
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

    public List<CatAula> findCatAulaEntities() {
        return findCatAulaEntities(true, -1, -1);
    }

    public List<CatAula> findCatAulaEntities(int maxResults, int firstResult) {
        return findCatAulaEntities(false, maxResults, firstResult);
    }

    private List<CatAula> findCatAulaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CatAula.class));
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

    public CatAula findCatAula(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CatAula.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatAulaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CatAula> rt = cq.from(CatAula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
