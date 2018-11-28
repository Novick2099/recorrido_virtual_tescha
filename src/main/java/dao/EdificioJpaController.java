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
import entidades.Edificio;
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
public class EdificioJpaController implements Serializable {

    public EdificioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Edificio edificio) throws RollbackFailureException, Exception {
        if (edificio.getAulaCollection() == null) {
            edificio.setAulaCollection(new ArrayList<Aula>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Aula> attachedAulaCollection = new ArrayList<Aula>();
            for (Aula aulaCollectionAulaToAttach : edificio.getAulaCollection()) {
                aulaCollectionAulaToAttach = em.getReference(aulaCollectionAulaToAttach.getClass(), aulaCollectionAulaToAttach.getIdAula());
                attachedAulaCollection.add(aulaCollectionAulaToAttach);
            }
            edificio.setAulaCollection(attachedAulaCollection);
            em.persist(edificio);
            for (Aula aulaCollectionAula : edificio.getAulaCollection()) {
                Edificio oldFkEdificioOfAulaCollectionAula = aulaCollectionAula.getFkEdificio();
                aulaCollectionAula.setFkEdificio(edificio);
                aulaCollectionAula = em.merge(aulaCollectionAula);
                if (oldFkEdificioOfAulaCollectionAula != null) {
                    oldFkEdificioOfAulaCollectionAula.getAulaCollection().remove(aulaCollectionAula);
                    oldFkEdificioOfAulaCollectionAula = em.merge(oldFkEdificioOfAulaCollectionAula);
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

    public void edit(Edificio edificio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Edificio persistentEdificio = em.find(Edificio.class, edificio.getIdEdificio());
            Collection<Aula> aulaCollectionOld = persistentEdificio.getAulaCollection();
            Collection<Aula> aulaCollectionNew = edificio.getAulaCollection();
            Collection<Aula> attachedAulaCollectionNew = new ArrayList<Aula>();
            for (Aula aulaCollectionNewAulaToAttach : aulaCollectionNew) {
                aulaCollectionNewAulaToAttach = em.getReference(aulaCollectionNewAulaToAttach.getClass(), aulaCollectionNewAulaToAttach.getIdAula());
                attachedAulaCollectionNew.add(aulaCollectionNewAulaToAttach);
            }
            aulaCollectionNew = attachedAulaCollectionNew;
            edificio.setAulaCollection(aulaCollectionNew);
            edificio = em.merge(edificio);
            for (Aula aulaCollectionOldAula : aulaCollectionOld) {
                if (!aulaCollectionNew.contains(aulaCollectionOldAula)) {
                    aulaCollectionOldAula.setFkEdificio(null);
                    aulaCollectionOldAula = em.merge(aulaCollectionOldAula);
                }
            }
            for (Aula aulaCollectionNewAula : aulaCollectionNew) {
                if (!aulaCollectionOld.contains(aulaCollectionNewAula)) {
                    Edificio oldFkEdificioOfAulaCollectionNewAula = aulaCollectionNewAula.getFkEdificio();
                    aulaCollectionNewAula.setFkEdificio(edificio);
                    aulaCollectionNewAula = em.merge(aulaCollectionNewAula);
                    if (oldFkEdificioOfAulaCollectionNewAula != null && !oldFkEdificioOfAulaCollectionNewAula.equals(edificio)) {
                        oldFkEdificioOfAulaCollectionNewAula.getAulaCollection().remove(aulaCollectionNewAula);
                        oldFkEdificioOfAulaCollectionNewAula = em.merge(oldFkEdificioOfAulaCollectionNewAula);
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
                Integer id = edificio.getIdEdificio();
                if (findEdificio(id) == null) {
                    throw new NonexistentEntityException("The edificio with id " + id + " no longer exists.");
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
            Edificio edificio;
            try {
                edificio = em.getReference(Edificio.class, id);
                edificio.getIdEdificio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The edificio with id " + id + " no longer exists.", enfe);
            }
            Collection<Aula> aulaCollection = edificio.getAulaCollection();
            for (Aula aulaCollectionAula : aulaCollection) {
                aulaCollectionAula.setFkEdificio(null);
                aulaCollectionAula = em.merge(aulaCollectionAula);
            }
            em.remove(edificio);
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

    public List<Edificio> findEdificioEntities() {
        return findEdificioEntities(true, -1, -1);
    }

    public List<Edificio> findEdificioEntities(int maxResults, int firstResult) {
        return findEdificioEntities(false, maxResults, firstResult);
    }

    private List<Edificio> findEdificioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Edificio.class));
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

    public Edificio findEdificio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Edificio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEdificioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Edificio> rt = cq.from(Edificio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
