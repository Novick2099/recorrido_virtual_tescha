/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Materia;
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
public class MateriaJpaController implements Serializable {

    public MateriaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materia materia) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (materia.getRubricaCollection() == null) {
            materia.setRubricaCollection(new ArrayList<Rubrica>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Rubrica> attachedRubricaCollection = new ArrayList<Rubrica>();
            for (Rubrica rubricaCollectionRubricaToAttach : materia.getRubricaCollection()) {
                rubricaCollectionRubricaToAttach = em.getReference(rubricaCollectionRubricaToAttach.getClass(), rubricaCollectionRubricaToAttach.getIdRubrica());
                attachedRubricaCollection.add(rubricaCollectionRubricaToAttach);
            }
            materia.setRubricaCollection(attachedRubricaCollection);
            em.persist(materia);
            for (Rubrica rubricaCollectionRubrica : materia.getRubricaCollection()) {
                Materia oldFkMateriaOfRubricaCollectionRubrica = rubricaCollectionRubrica.getFkMateria();
                rubricaCollectionRubrica.setFkMateria(materia);
                rubricaCollectionRubrica = em.merge(rubricaCollectionRubrica);
                if (oldFkMateriaOfRubricaCollectionRubrica != null) {
                    oldFkMateriaOfRubricaCollectionRubrica.getRubricaCollection().remove(rubricaCollectionRubrica);
                    oldFkMateriaOfRubricaCollectionRubrica = em.merge(oldFkMateriaOfRubricaCollectionRubrica);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findMateria(materia.getClaveMateria()) != null) {
                throw new PreexistingEntityException("Materia " + materia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materia materia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Materia persistentMateria = em.find(Materia.class, materia.getClaveMateria());
            Collection<Rubrica> rubricaCollectionOld = persistentMateria.getRubricaCollection();
            Collection<Rubrica> rubricaCollectionNew = materia.getRubricaCollection();
            Collection<Rubrica> attachedRubricaCollectionNew = new ArrayList<Rubrica>();
            for (Rubrica rubricaCollectionNewRubricaToAttach : rubricaCollectionNew) {
                rubricaCollectionNewRubricaToAttach = em.getReference(rubricaCollectionNewRubricaToAttach.getClass(), rubricaCollectionNewRubricaToAttach.getIdRubrica());
                attachedRubricaCollectionNew.add(rubricaCollectionNewRubricaToAttach);
            }
            rubricaCollectionNew = attachedRubricaCollectionNew;
            materia.setRubricaCollection(rubricaCollectionNew);
            materia = em.merge(materia);
            for (Rubrica rubricaCollectionOldRubrica : rubricaCollectionOld) {
                if (!rubricaCollectionNew.contains(rubricaCollectionOldRubrica)) {
                    rubricaCollectionOldRubrica.setFkMateria(null);
                    rubricaCollectionOldRubrica = em.merge(rubricaCollectionOldRubrica);
                }
            }
            for (Rubrica rubricaCollectionNewRubrica : rubricaCollectionNew) {
                if (!rubricaCollectionOld.contains(rubricaCollectionNewRubrica)) {
                    Materia oldFkMateriaOfRubricaCollectionNewRubrica = rubricaCollectionNewRubrica.getFkMateria();
                    rubricaCollectionNewRubrica.setFkMateria(materia);
                    rubricaCollectionNewRubrica = em.merge(rubricaCollectionNewRubrica);
                    if (oldFkMateriaOfRubricaCollectionNewRubrica != null && !oldFkMateriaOfRubricaCollectionNewRubrica.equals(materia)) {
                        oldFkMateriaOfRubricaCollectionNewRubrica.getRubricaCollection().remove(rubricaCollectionNewRubrica);
                        oldFkMateriaOfRubricaCollectionNewRubrica = em.merge(oldFkMateriaOfRubricaCollectionNewRubrica);
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
                String id = materia.getClaveMateria();
                if (findMateria(id) == null) {
                    throw new NonexistentEntityException("The materia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Materia materia;
            try {
                materia = em.getReference(Materia.class, id);
                materia.getClaveMateria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materia with id " + id + " no longer exists.", enfe);
            }
            Collection<Rubrica> rubricaCollection = materia.getRubricaCollection();
            for (Rubrica rubricaCollectionRubrica : rubricaCollection) {
                rubricaCollectionRubrica.setFkMateria(null);
                rubricaCollectionRubrica = em.merge(rubricaCollectionRubrica);
            }
            em.remove(materia);
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

    public List<Materia> findMateriaEntities() {
        return findMateriaEntities(true, -1, -1);
    }

    public List<Materia> findMateriaEntities(int maxResults, int firstResult) {
        return findMateriaEntities(false, maxResults, firstResult);
    }

    private List<Materia> findMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materia.class));
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

    public Materia findMateria(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materia> rt = cq.from(Materia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
