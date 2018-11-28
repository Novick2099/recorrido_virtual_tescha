/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Curso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Rubrica;
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
public class CursoJpaController implements Serializable {

    public CursoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) throws RollbackFailureException, Exception {
        if (curso.getHorarioCargoCollection() == null) {
            curso.setHorarioCargoCollection(new ArrayList<HorarioCargo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Rubrica fkRubrica = curso.getFkRubrica();
            if (fkRubrica != null) {
                fkRubrica = em.getReference(fkRubrica.getClass(), fkRubrica.getIdRubrica());
                curso.setFkRubrica(fkRubrica);
            }
            Collection<HorarioCargo> attachedHorarioCargoCollection = new ArrayList<HorarioCargo>();
            for (HorarioCargo horarioCargoCollectionHorarioCargoToAttach : curso.getHorarioCargoCollection()) {
                horarioCargoCollectionHorarioCargoToAttach = em.getReference(horarioCargoCollectionHorarioCargoToAttach.getClass(), horarioCargoCollectionHorarioCargoToAttach.getIdHorarioCargo());
                attachedHorarioCargoCollection.add(horarioCargoCollectionHorarioCargoToAttach);
            }
            curso.setHorarioCargoCollection(attachedHorarioCargoCollection);
            em.persist(curso);
            if (fkRubrica != null) {
                fkRubrica.getCursoCollection().add(curso);
                fkRubrica = em.merge(fkRubrica);
            }
            for (HorarioCargo horarioCargoCollectionHorarioCargo : curso.getHorarioCargoCollection()) {
                horarioCargoCollectionHorarioCargo.getCursoCollection().add(curso);
                horarioCargoCollectionHorarioCargo = em.merge(horarioCargoCollectionHorarioCargo);
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

    public void edit(Curso curso) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Curso persistentCurso = em.find(Curso.class, curso.getIdCurso());
            Rubrica fkRubricaOld = persistentCurso.getFkRubrica();
            Rubrica fkRubricaNew = curso.getFkRubrica();
            Collection<HorarioCargo> horarioCargoCollectionOld = persistentCurso.getHorarioCargoCollection();
            Collection<HorarioCargo> horarioCargoCollectionNew = curso.getHorarioCargoCollection();
            if (fkRubricaNew != null) {
                fkRubricaNew = em.getReference(fkRubricaNew.getClass(), fkRubricaNew.getIdRubrica());
                curso.setFkRubrica(fkRubricaNew);
            }
            Collection<HorarioCargo> attachedHorarioCargoCollectionNew = new ArrayList<HorarioCargo>();
            for (HorarioCargo horarioCargoCollectionNewHorarioCargoToAttach : horarioCargoCollectionNew) {
                horarioCargoCollectionNewHorarioCargoToAttach = em.getReference(horarioCargoCollectionNewHorarioCargoToAttach.getClass(), horarioCargoCollectionNewHorarioCargoToAttach.getIdHorarioCargo());
                attachedHorarioCargoCollectionNew.add(horarioCargoCollectionNewHorarioCargoToAttach);
            }
            horarioCargoCollectionNew = attachedHorarioCargoCollectionNew;
            curso.setHorarioCargoCollection(horarioCargoCollectionNew);
            curso = em.merge(curso);
            if (fkRubricaOld != null && !fkRubricaOld.equals(fkRubricaNew)) {
                fkRubricaOld.getCursoCollection().remove(curso);
                fkRubricaOld = em.merge(fkRubricaOld);
            }
            if (fkRubricaNew != null && !fkRubricaNew.equals(fkRubricaOld)) {
                fkRubricaNew.getCursoCollection().add(curso);
                fkRubricaNew = em.merge(fkRubricaNew);
            }
            for (HorarioCargo horarioCargoCollectionOldHorarioCargo : horarioCargoCollectionOld) {
                if (!horarioCargoCollectionNew.contains(horarioCargoCollectionOldHorarioCargo)) {
                    horarioCargoCollectionOldHorarioCargo.getCursoCollection().remove(curso);
                    horarioCargoCollectionOldHorarioCargo = em.merge(horarioCargoCollectionOldHorarioCargo);
                }
            }
            for (HorarioCargo horarioCargoCollectionNewHorarioCargo : horarioCargoCollectionNew) {
                if (!horarioCargoCollectionOld.contains(horarioCargoCollectionNewHorarioCargo)) {
                    horarioCargoCollectionNewHorarioCargo.getCursoCollection().add(curso);
                    horarioCargoCollectionNewHorarioCargo = em.merge(horarioCargoCollectionNewHorarioCargo);
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
                Integer id = curso.getIdCurso();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getIdCurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            Rubrica fkRubrica = curso.getFkRubrica();
            if (fkRubrica != null) {
                fkRubrica.getCursoCollection().remove(curso);
                fkRubrica = em.merge(fkRubrica);
            }
            Collection<HorarioCargo> horarioCargoCollection = curso.getHorarioCargoCollection();
            for (HorarioCargo horarioCargoCollectionHorarioCargo : horarioCargoCollection) {
                horarioCargoCollectionHorarioCargo.getCursoCollection().remove(curso);
                horarioCargoCollectionHorarioCargo = em.merge(horarioCargoCollectionHorarioCargo);
            }
            em.remove(curso);
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

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
