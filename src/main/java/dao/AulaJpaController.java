/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Aula;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.CatAula;
import entidades.Edificio;
import entidades.HorarioCargo;
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
public class AulaJpaController implements Serializable {

    public AulaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aula aula) throws RollbackFailureException, Exception {
        if (aula.getHorarioCargoCollection() == null) {
            aula.setHorarioCargoCollection(new ArrayList<HorarioCargo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CatAula fkCatAula = aula.getFkCatAula();
            if (fkCatAula != null) {
                fkCatAula = em.getReference(fkCatAula.getClass(), fkCatAula.getIdCatAula());
                aula.setFkCatAula(fkCatAula);
            }
            Edificio fkEdificio = aula.getFkEdificio();
            if (fkEdificio != null) {
                fkEdificio = em.getReference(fkEdificio.getClass(), fkEdificio.getIdEdificio());
                aula.setFkEdificio(fkEdificio);
            }
            Collection<HorarioCargo> attachedHorarioCargoCollection = new ArrayList<HorarioCargo>();
            for (HorarioCargo horarioCargoCollectionHorarioCargoToAttach : aula.getHorarioCargoCollection()) {
                horarioCargoCollectionHorarioCargoToAttach = em.getReference(horarioCargoCollectionHorarioCargoToAttach.getClass(), horarioCargoCollectionHorarioCargoToAttach.getIdHorarioCargo());
                attachedHorarioCargoCollection.add(horarioCargoCollectionHorarioCargoToAttach);
            }
            aula.setHorarioCargoCollection(attachedHorarioCargoCollection);
            em.persist(aula);
            if (fkCatAula != null) {
                fkCatAula.getAulaCollection().add(aula);
                fkCatAula = em.merge(fkCatAula);
            }
            if (fkEdificio != null) {
                fkEdificio.getAulaCollection().add(aula);
                fkEdificio = em.merge(fkEdificio);
            }
            for (HorarioCargo horarioCargoCollectionHorarioCargo : aula.getHorarioCargoCollection()) {
                Aula oldFkAulaOfHorarioCargoCollectionHorarioCargo = horarioCargoCollectionHorarioCargo.getFkAula();
                horarioCargoCollectionHorarioCargo.setFkAula(aula);
                horarioCargoCollectionHorarioCargo = em.merge(horarioCargoCollectionHorarioCargo);
                if (oldFkAulaOfHorarioCargoCollectionHorarioCargo != null) {
                    oldFkAulaOfHorarioCargoCollectionHorarioCargo.getHorarioCargoCollection().remove(horarioCargoCollectionHorarioCargo);
                    oldFkAulaOfHorarioCargoCollectionHorarioCargo = em.merge(oldFkAulaOfHorarioCargoCollectionHorarioCargo);
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

    public void edit(Aula aula) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Aula persistentAula = em.find(Aula.class, aula.getIdAula());
            CatAula fkCatAulaOld = persistentAula.getFkCatAula();
            CatAula fkCatAulaNew = aula.getFkCatAula();
            Edificio fkEdificioOld = persistentAula.getFkEdificio();
            Edificio fkEdificioNew = aula.getFkEdificio();
            Collection<HorarioCargo> horarioCargoCollectionOld = persistentAula.getHorarioCargoCollection();
            Collection<HorarioCargo> horarioCargoCollectionNew = aula.getHorarioCargoCollection();
            if (fkCatAulaNew != null) {
                fkCatAulaNew = em.getReference(fkCatAulaNew.getClass(), fkCatAulaNew.getIdCatAula());
                aula.setFkCatAula(fkCatAulaNew);
            }
            if (fkEdificioNew != null) {
                fkEdificioNew = em.getReference(fkEdificioNew.getClass(), fkEdificioNew.getIdEdificio());
                aula.setFkEdificio(fkEdificioNew);
            }
            Collection<HorarioCargo> attachedHorarioCargoCollectionNew = new ArrayList<HorarioCargo>();
            for (HorarioCargo horarioCargoCollectionNewHorarioCargoToAttach : horarioCargoCollectionNew) {
                horarioCargoCollectionNewHorarioCargoToAttach = em.getReference(horarioCargoCollectionNewHorarioCargoToAttach.getClass(), horarioCargoCollectionNewHorarioCargoToAttach.getIdHorarioCargo());
                attachedHorarioCargoCollectionNew.add(horarioCargoCollectionNewHorarioCargoToAttach);
            }
            horarioCargoCollectionNew = attachedHorarioCargoCollectionNew;
            aula.setHorarioCargoCollection(horarioCargoCollectionNew);
            aula = em.merge(aula);
            if (fkCatAulaOld != null && !fkCatAulaOld.equals(fkCatAulaNew)) {
                fkCatAulaOld.getAulaCollection().remove(aula);
                fkCatAulaOld = em.merge(fkCatAulaOld);
            }
            if (fkCatAulaNew != null && !fkCatAulaNew.equals(fkCatAulaOld)) {
                fkCatAulaNew.getAulaCollection().add(aula);
                fkCatAulaNew = em.merge(fkCatAulaNew);
            }
            if (fkEdificioOld != null && !fkEdificioOld.equals(fkEdificioNew)) {
                fkEdificioOld.getAulaCollection().remove(aula);
                fkEdificioOld = em.merge(fkEdificioOld);
            }
            if (fkEdificioNew != null && !fkEdificioNew.equals(fkEdificioOld)) {
                fkEdificioNew.getAulaCollection().add(aula);
                fkEdificioNew = em.merge(fkEdificioNew);
            }
            for (HorarioCargo horarioCargoCollectionOldHorarioCargo : horarioCargoCollectionOld) {
                if (!horarioCargoCollectionNew.contains(horarioCargoCollectionOldHorarioCargo)) {
                    horarioCargoCollectionOldHorarioCargo.setFkAula(null);
                    horarioCargoCollectionOldHorarioCargo = em.merge(horarioCargoCollectionOldHorarioCargo);
                }
            }
            for (HorarioCargo horarioCargoCollectionNewHorarioCargo : horarioCargoCollectionNew) {
                if (!horarioCargoCollectionOld.contains(horarioCargoCollectionNewHorarioCargo)) {
                    Aula oldFkAulaOfHorarioCargoCollectionNewHorarioCargo = horarioCargoCollectionNewHorarioCargo.getFkAula();
                    horarioCargoCollectionNewHorarioCargo.setFkAula(aula);
                    horarioCargoCollectionNewHorarioCargo = em.merge(horarioCargoCollectionNewHorarioCargo);
                    if (oldFkAulaOfHorarioCargoCollectionNewHorarioCargo != null && !oldFkAulaOfHorarioCargoCollectionNewHorarioCargo.equals(aula)) {
                        oldFkAulaOfHorarioCargoCollectionNewHorarioCargo.getHorarioCargoCollection().remove(horarioCargoCollectionNewHorarioCargo);
                        oldFkAulaOfHorarioCargoCollectionNewHorarioCargo = em.merge(oldFkAulaOfHorarioCargoCollectionNewHorarioCargo);
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
                Integer id = aula.getIdAula();
                if (findAula(id) == null) {
                    throw new NonexistentEntityException("The aula with id " + id + " no longer exists.");
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
            Aula aula;
            try {
                aula = em.getReference(Aula.class, id);
                aula.getIdAula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aula with id " + id + " no longer exists.", enfe);
            }
            CatAula fkCatAula = aula.getFkCatAula();
            if (fkCatAula != null) {
                fkCatAula.getAulaCollection().remove(aula);
                fkCatAula = em.merge(fkCatAula);
            }
            Edificio fkEdificio = aula.getFkEdificio();
            if (fkEdificio != null) {
                fkEdificio.getAulaCollection().remove(aula);
                fkEdificio = em.merge(fkEdificio);
            }
            Collection<HorarioCargo> horarioCargoCollection = aula.getHorarioCargoCollection();
            for (HorarioCargo horarioCargoCollectionHorarioCargo : horarioCargoCollection) {
                horarioCargoCollectionHorarioCargo.setFkAula(null);
                horarioCargoCollectionHorarioCargo = em.merge(horarioCargoCollectionHorarioCargo);
            }
            em.remove(aula);
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

    public List<Aula> findAulaEntities() {
        return findAulaEntities(true, -1, -1);
    }

    public List<Aula> findAulaEntities(int maxResults, int firstResult) {
        return findAulaEntities(false, maxResults, firstResult);
    }

    private List<Aula> findAulaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aula.class));
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

    public Aula findAula(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aula.class, id);
        } finally {
            em.close();
        }
    }

    public int getAulaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aula> rt = cq.from(Aula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
