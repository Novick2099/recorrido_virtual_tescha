/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Carrera;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Rubrica;
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
public class CarreraJpaController implements Serializable {

    public CarreraJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) throws RollbackFailureException, Exception {
        if (carrera.getRubricaCollection() == null) {
            carrera.setRubricaCollection(new ArrayList<Rubrica>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Rubrica> attachedRubricaCollection = new ArrayList<Rubrica>();
            for (Rubrica rubricaCollectionRubricaToAttach : carrera.getRubricaCollection()) {
                rubricaCollectionRubricaToAttach = em.getReference(rubricaCollectionRubricaToAttach.getClass(), rubricaCollectionRubricaToAttach.getIdRubrica());
                attachedRubricaCollection.add(rubricaCollectionRubricaToAttach);
            }
            carrera.setRubricaCollection(attachedRubricaCollection);
            em.persist(carrera);
            for (Rubrica rubricaCollectionRubrica : carrera.getRubricaCollection()) {
                Carrera oldFkCarreraOfRubricaCollectionRubrica = rubricaCollectionRubrica.getFkCarrera();
                rubricaCollectionRubrica.setFkCarrera(carrera);
                rubricaCollectionRubrica = em.merge(rubricaCollectionRubrica);
                if (oldFkCarreraOfRubricaCollectionRubrica != null) {
                    oldFkCarreraOfRubricaCollectionRubrica.getRubricaCollection().remove(rubricaCollectionRubrica);
                    oldFkCarreraOfRubricaCollectionRubrica = em.merge(oldFkCarreraOfRubricaCollectionRubrica);
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

    public void edit(Carrera carrera) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getIdCarrera());
            Collection<Rubrica> rubricaCollectionOld = persistentCarrera.getRubricaCollection();
            Collection<Rubrica> rubricaCollectionNew = carrera.getRubricaCollection();
            Collection<Rubrica> attachedRubricaCollectionNew = new ArrayList<Rubrica>();
            for (Rubrica rubricaCollectionNewRubricaToAttach : rubricaCollectionNew) {
                rubricaCollectionNewRubricaToAttach = em.getReference(rubricaCollectionNewRubricaToAttach.getClass(), rubricaCollectionNewRubricaToAttach.getIdRubrica());
                attachedRubricaCollectionNew.add(rubricaCollectionNewRubricaToAttach);
            }
            rubricaCollectionNew = attachedRubricaCollectionNew;
            carrera.setRubricaCollection(rubricaCollectionNew);
            carrera = em.merge(carrera);
            for (Rubrica rubricaCollectionOldRubrica : rubricaCollectionOld) {
                if (!rubricaCollectionNew.contains(rubricaCollectionOldRubrica)) {
                    rubricaCollectionOldRubrica.setFkCarrera(null);
                    rubricaCollectionOldRubrica = em.merge(rubricaCollectionOldRubrica);
                }
            }
            for (Rubrica rubricaCollectionNewRubrica : rubricaCollectionNew) {
                if (!rubricaCollectionOld.contains(rubricaCollectionNewRubrica)) {
                    Carrera oldFkCarreraOfRubricaCollectionNewRubrica = rubricaCollectionNewRubrica.getFkCarrera();
                    rubricaCollectionNewRubrica.setFkCarrera(carrera);
                    rubricaCollectionNewRubrica = em.merge(rubricaCollectionNewRubrica);
                    if (oldFkCarreraOfRubricaCollectionNewRubrica != null && !oldFkCarreraOfRubricaCollectionNewRubrica.equals(carrera)) {
                        oldFkCarreraOfRubricaCollectionNewRubrica.getRubricaCollection().remove(rubricaCollectionNewRubrica);
                        oldFkCarreraOfRubricaCollectionNewRubrica = em.merge(oldFkCarreraOfRubricaCollectionNewRubrica);
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
                Integer id = carrera.getIdCarrera();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
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
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getIdCarrera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            Collection<Rubrica> rubricaCollection = carrera.getRubricaCollection();
            for (Rubrica rubricaCollectionRubrica : rubricaCollection) {
                rubricaCollectionRubrica.setFkCarrera(null);
                rubricaCollectionRubrica = em.merge(rubricaCollectionRubrica);
            }
            em.remove(carrera);
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

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
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

    public Carrera findCarrera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
