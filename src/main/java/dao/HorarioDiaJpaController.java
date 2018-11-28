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
import entidades.Dia;
import entidades.Horario;
import entidades.HorarioCargo;
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
public class HorarioDiaJpaController implements Serializable {

    public HorarioDiaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HorarioDia horarioDia) throws RollbackFailureException, Exception {
        if (horarioDia.getHorarioCargoCollection() == null) {
            horarioDia.setHorarioCargoCollection(new ArrayList<HorarioCargo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Dia fkDia = horarioDia.getFkDia();
            if (fkDia != null) {
                fkDia = em.getReference(fkDia.getClass(), fkDia.getIdDia());
                horarioDia.setFkDia(fkDia);
            }
            Horario fkHorario = horarioDia.getFkHorario();
            if (fkHorario != null) {
                fkHorario = em.getReference(fkHorario.getClass(), fkHorario.getIdHorario());
                horarioDia.setFkHorario(fkHorario);
            }
            Collection<HorarioCargo> attachedHorarioCargoCollection = new ArrayList<HorarioCargo>();
            for (HorarioCargo horarioCargoCollectionHorarioCargoToAttach : horarioDia.getHorarioCargoCollection()) {
                horarioCargoCollectionHorarioCargoToAttach = em.getReference(horarioCargoCollectionHorarioCargoToAttach.getClass(), horarioCargoCollectionHorarioCargoToAttach.getIdHorarioCargo());
                attachedHorarioCargoCollection.add(horarioCargoCollectionHorarioCargoToAttach);
            }
            horarioDia.setHorarioCargoCollection(attachedHorarioCargoCollection);
            em.persist(horarioDia);
            if (fkDia != null) {
                fkDia.getHorarioDiaCollection().add(horarioDia);
                fkDia = em.merge(fkDia);
            }
            if (fkHorario != null) {
                fkHorario.getHorarioDiaCollection().add(horarioDia);
                fkHorario = em.merge(fkHorario);
            }
            for (HorarioCargo horarioCargoCollectionHorarioCargo : horarioDia.getHorarioCargoCollection()) {
                HorarioDia oldFkHorarioDiaOfHorarioCargoCollectionHorarioCargo = horarioCargoCollectionHorarioCargo.getFkHorarioDia();
                horarioCargoCollectionHorarioCargo.setFkHorarioDia(horarioDia);
                horarioCargoCollectionHorarioCargo = em.merge(horarioCargoCollectionHorarioCargo);
                if (oldFkHorarioDiaOfHorarioCargoCollectionHorarioCargo != null) {
                    oldFkHorarioDiaOfHorarioCargoCollectionHorarioCargo.getHorarioCargoCollection().remove(horarioCargoCollectionHorarioCargo);
                    oldFkHorarioDiaOfHorarioCargoCollectionHorarioCargo = em.merge(oldFkHorarioDiaOfHorarioCargoCollectionHorarioCargo);
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

    public void edit(HorarioDia horarioDia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HorarioDia persistentHorarioDia = em.find(HorarioDia.class, horarioDia.getIdHorarioDia());
            Dia fkDiaOld = persistentHorarioDia.getFkDia();
            Dia fkDiaNew = horarioDia.getFkDia();
            Horario fkHorarioOld = persistentHorarioDia.getFkHorario();
            Horario fkHorarioNew = horarioDia.getFkHorario();
            Collection<HorarioCargo> horarioCargoCollectionOld = persistentHorarioDia.getHorarioCargoCollection();
            Collection<HorarioCargo> horarioCargoCollectionNew = horarioDia.getHorarioCargoCollection();
            if (fkDiaNew != null) {
                fkDiaNew = em.getReference(fkDiaNew.getClass(), fkDiaNew.getIdDia());
                horarioDia.setFkDia(fkDiaNew);
            }
            if (fkHorarioNew != null) {
                fkHorarioNew = em.getReference(fkHorarioNew.getClass(), fkHorarioNew.getIdHorario());
                horarioDia.setFkHorario(fkHorarioNew);
            }
            Collection<HorarioCargo> attachedHorarioCargoCollectionNew = new ArrayList<HorarioCargo>();
            for (HorarioCargo horarioCargoCollectionNewHorarioCargoToAttach : horarioCargoCollectionNew) {
                horarioCargoCollectionNewHorarioCargoToAttach = em.getReference(horarioCargoCollectionNewHorarioCargoToAttach.getClass(), horarioCargoCollectionNewHorarioCargoToAttach.getIdHorarioCargo());
                attachedHorarioCargoCollectionNew.add(horarioCargoCollectionNewHorarioCargoToAttach);
            }
            horarioCargoCollectionNew = attachedHorarioCargoCollectionNew;
            horarioDia.setHorarioCargoCollection(horarioCargoCollectionNew);
            horarioDia = em.merge(horarioDia);
            if (fkDiaOld != null && !fkDiaOld.equals(fkDiaNew)) {
                fkDiaOld.getHorarioDiaCollection().remove(horarioDia);
                fkDiaOld = em.merge(fkDiaOld);
            }
            if (fkDiaNew != null && !fkDiaNew.equals(fkDiaOld)) {
                fkDiaNew.getHorarioDiaCollection().add(horarioDia);
                fkDiaNew = em.merge(fkDiaNew);
            }
            if (fkHorarioOld != null && !fkHorarioOld.equals(fkHorarioNew)) {
                fkHorarioOld.getHorarioDiaCollection().remove(horarioDia);
                fkHorarioOld = em.merge(fkHorarioOld);
            }
            if (fkHorarioNew != null && !fkHorarioNew.equals(fkHorarioOld)) {
                fkHorarioNew.getHorarioDiaCollection().add(horarioDia);
                fkHorarioNew = em.merge(fkHorarioNew);
            }
            for (HorarioCargo horarioCargoCollectionOldHorarioCargo : horarioCargoCollectionOld) {
                if (!horarioCargoCollectionNew.contains(horarioCargoCollectionOldHorarioCargo)) {
                    horarioCargoCollectionOldHorarioCargo.setFkHorarioDia(null);
                    horarioCargoCollectionOldHorarioCargo = em.merge(horarioCargoCollectionOldHorarioCargo);
                }
            }
            for (HorarioCargo horarioCargoCollectionNewHorarioCargo : horarioCargoCollectionNew) {
                if (!horarioCargoCollectionOld.contains(horarioCargoCollectionNewHorarioCargo)) {
                    HorarioDia oldFkHorarioDiaOfHorarioCargoCollectionNewHorarioCargo = horarioCargoCollectionNewHorarioCargo.getFkHorarioDia();
                    horarioCargoCollectionNewHorarioCargo.setFkHorarioDia(horarioDia);
                    horarioCargoCollectionNewHorarioCargo = em.merge(horarioCargoCollectionNewHorarioCargo);
                    if (oldFkHorarioDiaOfHorarioCargoCollectionNewHorarioCargo != null && !oldFkHorarioDiaOfHorarioCargoCollectionNewHorarioCargo.equals(horarioDia)) {
                        oldFkHorarioDiaOfHorarioCargoCollectionNewHorarioCargo.getHorarioCargoCollection().remove(horarioCargoCollectionNewHorarioCargo);
                        oldFkHorarioDiaOfHorarioCargoCollectionNewHorarioCargo = em.merge(oldFkHorarioDiaOfHorarioCargoCollectionNewHorarioCargo);
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
                Integer id = horarioDia.getIdHorarioDia();
                if (findHorarioDia(id) == null) {
                    throw new NonexistentEntityException("The horarioDia with id " + id + " no longer exists.");
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
            HorarioDia horarioDia;
            try {
                horarioDia = em.getReference(HorarioDia.class, id);
                horarioDia.getIdHorarioDia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarioDia with id " + id + " no longer exists.", enfe);
            }
            Dia fkDia = horarioDia.getFkDia();
            if (fkDia != null) {
                fkDia.getHorarioDiaCollection().remove(horarioDia);
                fkDia = em.merge(fkDia);
            }
            Horario fkHorario = horarioDia.getFkHorario();
            if (fkHorario != null) {
                fkHorario.getHorarioDiaCollection().remove(horarioDia);
                fkHorario = em.merge(fkHorario);
            }
            Collection<HorarioCargo> horarioCargoCollection = horarioDia.getHorarioCargoCollection();
            for (HorarioCargo horarioCargoCollectionHorarioCargo : horarioCargoCollection) {
                horarioCargoCollectionHorarioCargo.setFkHorarioDia(null);
                horarioCargoCollectionHorarioCargo = em.merge(horarioCargoCollectionHorarioCargo);
            }
            em.remove(horarioDia);
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

    public List<HorarioDia> findHorarioDiaEntities() {
        return findHorarioDiaEntities(true, -1, -1);
    }

    public List<HorarioDia> findHorarioDiaEntities(int maxResults, int firstResult) {
        return findHorarioDiaEntities(false, maxResults, firstResult);
    }

    private List<HorarioDia> findHorarioDiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HorarioDia.class));
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

    public HorarioDia findHorarioDia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HorarioDia.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioDiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HorarioDia> rt = cq.from(HorarioDia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
