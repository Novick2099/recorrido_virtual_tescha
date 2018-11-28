/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Dia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.HorarioDia;
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
public class DiaJpaController implements Serializable {

    public DiaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dia dia) throws RollbackFailureException, Exception {
        if (dia.getHorarioDiaCollection() == null) {
            dia.setHorarioDiaCollection(new ArrayList<HorarioDia>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<HorarioDia> attachedHorarioDiaCollection = new ArrayList<HorarioDia>();
            for (HorarioDia horarioDiaCollectionHorarioDiaToAttach : dia.getHorarioDiaCollection()) {
                horarioDiaCollectionHorarioDiaToAttach = em.getReference(horarioDiaCollectionHorarioDiaToAttach.getClass(), horarioDiaCollectionHorarioDiaToAttach.getIdHorarioDia());
                attachedHorarioDiaCollection.add(horarioDiaCollectionHorarioDiaToAttach);
            }
            dia.setHorarioDiaCollection(attachedHorarioDiaCollection);
            em.persist(dia);
            for (HorarioDia horarioDiaCollectionHorarioDia : dia.getHorarioDiaCollection()) {
                Dia oldFkDiaOfHorarioDiaCollectionHorarioDia = horarioDiaCollectionHorarioDia.getFkDia();
                horarioDiaCollectionHorarioDia.setFkDia(dia);
                horarioDiaCollectionHorarioDia = em.merge(horarioDiaCollectionHorarioDia);
                if (oldFkDiaOfHorarioDiaCollectionHorarioDia != null) {
                    oldFkDiaOfHorarioDiaCollectionHorarioDia.getHorarioDiaCollection().remove(horarioDiaCollectionHorarioDia);
                    oldFkDiaOfHorarioDiaCollectionHorarioDia = em.merge(oldFkDiaOfHorarioDiaCollectionHorarioDia);
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

    public void edit(Dia dia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Dia persistentDia = em.find(Dia.class, dia.getIdDia());
            Collection<HorarioDia> horarioDiaCollectionOld = persistentDia.getHorarioDiaCollection();
            Collection<HorarioDia> horarioDiaCollectionNew = dia.getHorarioDiaCollection();
            Collection<HorarioDia> attachedHorarioDiaCollectionNew = new ArrayList<HorarioDia>();
            for (HorarioDia horarioDiaCollectionNewHorarioDiaToAttach : horarioDiaCollectionNew) {
                horarioDiaCollectionNewHorarioDiaToAttach = em.getReference(horarioDiaCollectionNewHorarioDiaToAttach.getClass(), horarioDiaCollectionNewHorarioDiaToAttach.getIdHorarioDia());
                attachedHorarioDiaCollectionNew.add(horarioDiaCollectionNewHorarioDiaToAttach);
            }
            horarioDiaCollectionNew = attachedHorarioDiaCollectionNew;
            dia.setHorarioDiaCollection(horarioDiaCollectionNew);
            dia = em.merge(dia);
            for (HorarioDia horarioDiaCollectionOldHorarioDia : horarioDiaCollectionOld) {
                if (!horarioDiaCollectionNew.contains(horarioDiaCollectionOldHorarioDia)) {
                    horarioDiaCollectionOldHorarioDia.setFkDia(null);
                    horarioDiaCollectionOldHorarioDia = em.merge(horarioDiaCollectionOldHorarioDia);
                }
            }
            for (HorarioDia horarioDiaCollectionNewHorarioDia : horarioDiaCollectionNew) {
                if (!horarioDiaCollectionOld.contains(horarioDiaCollectionNewHorarioDia)) {
                    Dia oldFkDiaOfHorarioDiaCollectionNewHorarioDia = horarioDiaCollectionNewHorarioDia.getFkDia();
                    horarioDiaCollectionNewHorarioDia.setFkDia(dia);
                    horarioDiaCollectionNewHorarioDia = em.merge(horarioDiaCollectionNewHorarioDia);
                    if (oldFkDiaOfHorarioDiaCollectionNewHorarioDia != null && !oldFkDiaOfHorarioDiaCollectionNewHorarioDia.equals(dia)) {
                        oldFkDiaOfHorarioDiaCollectionNewHorarioDia.getHorarioDiaCollection().remove(horarioDiaCollectionNewHorarioDia);
                        oldFkDiaOfHorarioDiaCollectionNewHorarioDia = em.merge(oldFkDiaOfHorarioDiaCollectionNewHorarioDia);
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
                Integer id = dia.getIdDia();
                if (findDia(id) == null) {
                    throw new NonexistentEntityException("The dia with id " + id + " no longer exists.");
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
            Dia dia;
            try {
                dia = em.getReference(Dia.class, id);
                dia.getIdDia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dia with id " + id + " no longer exists.", enfe);
            }
            Collection<HorarioDia> horarioDiaCollection = dia.getHorarioDiaCollection();
            for (HorarioDia horarioDiaCollectionHorarioDia : horarioDiaCollection) {
                horarioDiaCollectionHorarioDia.setFkDia(null);
                horarioDiaCollectionHorarioDia = em.merge(horarioDiaCollectionHorarioDia);
            }
            em.remove(dia);
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

    public List<Dia> findDiaEntities() {
        return findDiaEntities(true, -1, -1);
    }

    public List<Dia> findDiaEntities(int maxResults, int firstResult) {
        return findDiaEntities(false, maxResults, firstResult);
    }

    private List<Dia> findDiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dia.class));
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

    public Dia findDia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dia.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dia> rt = cq.from(Dia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
