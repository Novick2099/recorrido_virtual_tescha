/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Horario;
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
public class HorarioJpaController implements Serializable {

    public HorarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horario horario) throws RollbackFailureException, Exception {
        if (horario.getHorarioDiaCollection() == null) {
            horario.setHorarioDiaCollection(new ArrayList<HorarioDia>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<HorarioDia> attachedHorarioDiaCollection = new ArrayList<HorarioDia>();
            for (HorarioDia horarioDiaCollectionHorarioDiaToAttach : horario.getHorarioDiaCollection()) {
                horarioDiaCollectionHorarioDiaToAttach = em.getReference(horarioDiaCollectionHorarioDiaToAttach.getClass(), horarioDiaCollectionHorarioDiaToAttach.getIdHorarioDia());
                attachedHorarioDiaCollection.add(horarioDiaCollectionHorarioDiaToAttach);
            }
            horario.setHorarioDiaCollection(attachedHorarioDiaCollection);
            em.persist(horario);
            for (HorarioDia horarioDiaCollectionHorarioDia : horario.getHorarioDiaCollection()) {
                Horario oldFkHorarioOfHorarioDiaCollectionHorarioDia = horarioDiaCollectionHorarioDia.getFkHorario();
                horarioDiaCollectionHorarioDia.setFkHorario(horario);
                horarioDiaCollectionHorarioDia = em.merge(horarioDiaCollectionHorarioDia);
                if (oldFkHorarioOfHorarioDiaCollectionHorarioDia != null) {
                    oldFkHorarioOfHorarioDiaCollectionHorarioDia.getHorarioDiaCollection().remove(horarioDiaCollectionHorarioDia);
                    oldFkHorarioOfHorarioDiaCollectionHorarioDia = em.merge(oldFkHorarioOfHorarioDiaCollectionHorarioDia);
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

    public void edit(Horario horario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Horario persistentHorario = em.find(Horario.class, horario.getIdHorario());
            Collection<HorarioDia> horarioDiaCollectionOld = persistentHorario.getHorarioDiaCollection();
            Collection<HorarioDia> horarioDiaCollectionNew = horario.getHorarioDiaCollection();
            Collection<HorarioDia> attachedHorarioDiaCollectionNew = new ArrayList<HorarioDia>();
            for (HorarioDia horarioDiaCollectionNewHorarioDiaToAttach : horarioDiaCollectionNew) {
                horarioDiaCollectionNewHorarioDiaToAttach = em.getReference(horarioDiaCollectionNewHorarioDiaToAttach.getClass(), horarioDiaCollectionNewHorarioDiaToAttach.getIdHorarioDia());
                attachedHorarioDiaCollectionNew.add(horarioDiaCollectionNewHorarioDiaToAttach);
            }
            horarioDiaCollectionNew = attachedHorarioDiaCollectionNew;
            horario.setHorarioDiaCollection(horarioDiaCollectionNew);
            horario = em.merge(horario);
            for (HorarioDia horarioDiaCollectionOldHorarioDia : horarioDiaCollectionOld) {
                if (!horarioDiaCollectionNew.contains(horarioDiaCollectionOldHorarioDia)) {
                    horarioDiaCollectionOldHorarioDia.setFkHorario(null);
                    horarioDiaCollectionOldHorarioDia = em.merge(horarioDiaCollectionOldHorarioDia);
                }
            }
            for (HorarioDia horarioDiaCollectionNewHorarioDia : horarioDiaCollectionNew) {
                if (!horarioDiaCollectionOld.contains(horarioDiaCollectionNewHorarioDia)) {
                    Horario oldFkHorarioOfHorarioDiaCollectionNewHorarioDia = horarioDiaCollectionNewHorarioDia.getFkHorario();
                    horarioDiaCollectionNewHorarioDia.setFkHorario(horario);
                    horarioDiaCollectionNewHorarioDia = em.merge(horarioDiaCollectionNewHorarioDia);
                    if (oldFkHorarioOfHorarioDiaCollectionNewHorarioDia != null && !oldFkHorarioOfHorarioDiaCollectionNewHorarioDia.equals(horario)) {
                        oldFkHorarioOfHorarioDiaCollectionNewHorarioDia.getHorarioDiaCollection().remove(horarioDiaCollectionNewHorarioDia);
                        oldFkHorarioOfHorarioDiaCollectionNewHorarioDia = em.merge(oldFkHorarioOfHorarioDiaCollectionNewHorarioDia);
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
                Integer id = horario.getIdHorario();
                if (findHorario(id) == null) {
                    throw new NonexistentEntityException("The horario with id " + id + " no longer exists.");
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
            Horario horario;
            try {
                horario = em.getReference(Horario.class, id);
                horario.getIdHorario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horario with id " + id + " no longer exists.", enfe);
            }
            Collection<HorarioDia> horarioDiaCollection = horario.getHorarioDiaCollection();
            for (HorarioDia horarioDiaCollectionHorarioDia : horarioDiaCollection) {
                horarioDiaCollectionHorarioDia.setFkHorario(null);
                horarioDiaCollectionHorarioDia = em.merge(horarioDiaCollectionHorarioDia);
            }
            em.remove(horario);
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

    public List<Horario> findHorarioEntities() {
        return findHorarioEntities(true, -1, -1);
    }

    public List<Horario> findHorarioEntities(int maxResults, int firstResult) {
        return findHorarioEntities(false, maxResults, firstResult);
    }

    private List<Horario> findHorarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horario.class));
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

    public Horario findHorario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horario.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horario> rt = cq.from(Horario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
