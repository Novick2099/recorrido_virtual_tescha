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
import entidades.EmpleadoCargo;
import entidades.HorarioDia;
import entidades.Curso;
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
public class HorarioCargoJpaController implements Serializable {

    public HorarioCargoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HorarioCargo horarioCargo) throws RollbackFailureException, Exception {
        if (horarioCargo.getCursoCollection() == null) {
            horarioCargo.setCursoCollection(new ArrayList<Curso>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Aula fkAula = horarioCargo.getFkAula();
            if (fkAula != null) {
                fkAula = em.getReference(fkAula.getClass(), fkAula.getIdAula());
                horarioCargo.setFkAula(fkAula);
            }
            EmpleadoCargo fkEmpleadoCargo = horarioCargo.getFkEmpleadoCargo();
            if (fkEmpleadoCargo != null) {
                fkEmpleadoCargo = em.getReference(fkEmpleadoCargo.getClass(), fkEmpleadoCargo.getIdEmpleadoCargo());
                horarioCargo.setFkEmpleadoCargo(fkEmpleadoCargo);
            }
            HorarioDia fkHorarioDia = horarioCargo.getFkHorarioDia();
            if (fkHorarioDia != null) {
                fkHorarioDia = em.getReference(fkHorarioDia.getClass(), fkHorarioDia.getIdHorarioDia());
                horarioCargo.setFkHorarioDia(fkHorarioDia);
            }
            Collection<Curso> attachedCursoCollection = new ArrayList<Curso>();
            for (Curso cursoCollectionCursoToAttach : horarioCargo.getCursoCollection()) {
                cursoCollectionCursoToAttach = em.getReference(cursoCollectionCursoToAttach.getClass(), cursoCollectionCursoToAttach.getIdCurso());
                attachedCursoCollection.add(cursoCollectionCursoToAttach);
            }
            horarioCargo.setCursoCollection(attachedCursoCollection);
            em.persist(horarioCargo);
            if (fkAula != null) {
                fkAula.getHorarioCargoCollection().add(horarioCargo);
                fkAula = em.merge(fkAula);
            }
            if (fkEmpleadoCargo != null) {
                fkEmpleadoCargo.getHorarioCargoCollection().add(horarioCargo);
                fkEmpleadoCargo = em.merge(fkEmpleadoCargo);
            }
            if (fkHorarioDia != null) {
                fkHorarioDia.getHorarioCargoCollection().add(horarioCargo);
                fkHorarioDia = em.merge(fkHorarioDia);
            }
            for (Curso cursoCollectionCurso : horarioCargo.getCursoCollection()) {
                cursoCollectionCurso.getHorarioCargoCollection().add(horarioCargo);
                cursoCollectionCurso = em.merge(cursoCollectionCurso);
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

    public void edit(HorarioCargo horarioCargo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HorarioCargo persistentHorarioCargo = em.find(HorarioCargo.class, horarioCargo.getIdHorarioCargo());
            Aula fkAulaOld = persistentHorarioCargo.getFkAula();
            Aula fkAulaNew = horarioCargo.getFkAula();
            EmpleadoCargo fkEmpleadoCargoOld = persistentHorarioCargo.getFkEmpleadoCargo();
            EmpleadoCargo fkEmpleadoCargoNew = horarioCargo.getFkEmpleadoCargo();
            HorarioDia fkHorarioDiaOld = persistentHorarioCargo.getFkHorarioDia();
            HorarioDia fkHorarioDiaNew = horarioCargo.getFkHorarioDia();
            Collection<Curso> cursoCollectionOld = persistentHorarioCargo.getCursoCollection();
            Collection<Curso> cursoCollectionNew = horarioCargo.getCursoCollection();
            if (fkAulaNew != null) {
                fkAulaNew = em.getReference(fkAulaNew.getClass(), fkAulaNew.getIdAula());
                horarioCargo.setFkAula(fkAulaNew);
            }
            if (fkEmpleadoCargoNew != null) {
                fkEmpleadoCargoNew = em.getReference(fkEmpleadoCargoNew.getClass(), fkEmpleadoCargoNew.getIdEmpleadoCargo());
                horarioCargo.setFkEmpleadoCargo(fkEmpleadoCargoNew);
            }
            if (fkHorarioDiaNew != null) {
                fkHorarioDiaNew = em.getReference(fkHorarioDiaNew.getClass(), fkHorarioDiaNew.getIdHorarioDia());
                horarioCargo.setFkHorarioDia(fkHorarioDiaNew);
            }
            Collection<Curso> attachedCursoCollectionNew = new ArrayList<Curso>();
            for (Curso cursoCollectionNewCursoToAttach : cursoCollectionNew) {
                cursoCollectionNewCursoToAttach = em.getReference(cursoCollectionNewCursoToAttach.getClass(), cursoCollectionNewCursoToAttach.getIdCurso());
                attachedCursoCollectionNew.add(cursoCollectionNewCursoToAttach);
            }
            cursoCollectionNew = attachedCursoCollectionNew;
            horarioCargo.setCursoCollection(cursoCollectionNew);
            horarioCargo = em.merge(horarioCargo);
            if (fkAulaOld != null && !fkAulaOld.equals(fkAulaNew)) {
                fkAulaOld.getHorarioCargoCollection().remove(horarioCargo);
                fkAulaOld = em.merge(fkAulaOld);
            }
            if (fkAulaNew != null && !fkAulaNew.equals(fkAulaOld)) {
                fkAulaNew.getHorarioCargoCollection().add(horarioCargo);
                fkAulaNew = em.merge(fkAulaNew);
            }
            if (fkEmpleadoCargoOld != null && !fkEmpleadoCargoOld.equals(fkEmpleadoCargoNew)) {
                fkEmpleadoCargoOld.getHorarioCargoCollection().remove(horarioCargo);
                fkEmpleadoCargoOld = em.merge(fkEmpleadoCargoOld);
            }
            if (fkEmpleadoCargoNew != null && !fkEmpleadoCargoNew.equals(fkEmpleadoCargoOld)) {
                fkEmpleadoCargoNew.getHorarioCargoCollection().add(horarioCargo);
                fkEmpleadoCargoNew = em.merge(fkEmpleadoCargoNew);
            }
            if (fkHorarioDiaOld != null && !fkHorarioDiaOld.equals(fkHorarioDiaNew)) {
                fkHorarioDiaOld.getHorarioCargoCollection().remove(horarioCargo);
                fkHorarioDiaOld = em.merge(fkHorarioDiaOld);
            }
            if (fkHorarioDiaNew != null && !fkHorarioDiaNew.equals(fkHorarioDiaOld)) {
                fkHorarioDiaNew.getHorarioCargoCollection().add(horarioCargo);
                fkHorarioDiaNew = em.merge(fkHorarioDiaNew);
            }
            for (Curso cursoCollectionOldCurso : cursoCollectionOld) {
                if (!cursoCollectionNew.contains(cursoCollectionOldCurso)) {
                    cursoCollectionOldCurso.getHorarioCargoCollection().remove(horarioCargo);
                    cursoCollectionOldCurso = em.merge(cursoCollectionOldCurso);
                }
            }
            for (Curso cursoCollectionNewCurso : cursoCollectionNew) {
                if (!cursoCollectionOld.contains(cursoCollectionNewCurso)) {
                    cursoCollectionNewCurso.getHorarioCargoCollection().add(horarioCargo);
                    cursoCollectionNewCurso = em.merge(cursoCollectionNewCurso);
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
                Integer id = horarioCargo.getIdHorarioCargo();
                if (findHorarioCargo(id) == null) {
                    throw new NonexistentEntityException("The horarioCargo with id " + id + " no longer exists.");
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
            HorarioCargo horarioCargo;
            try {
                horarioCargo = em.getReference(HorarioCargo.class, id);
                horarioCargo.getIdHorarioCargo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarioCargo with id " + id + " no longer exists.", enfe);
            }
            Aula fkAula = horarioCargo.getFkAula();
            if (fkAula != null) {
                fkAula.getHorarioCargoCollection().remove(horarioCargo);
                fkAula = em.merge(fkAula);
            }
            EmpleadoCargo fkEmpleadoCargo = horarioCargo.getFkEmpleadoCargo();
            if (fkEmpleadoCargo != null) {
                fkEmpleadoCargo.getHorarioCargoCollection().remove(horarioCargo);
                fkEmpleadoCargo = em.merge(fkEmpleadoCargo);
            }
            HorarioDia fkHorarioDia = horarioCargo.getFkHorarioDia();
            if (fkHorarioDia != null) {
                fkHorarioDia.getHorarioCargoCollection().remove(horarioCargo);
                fkHorarioDia = em.merge(fkHorarioDia);
            }
            Collection<Curso> cursoCollection = horarioCargo.getCursoCollection();
            for (Curso cursoCollectionCurso : cursoCollection) {
                cursoCollectionCurso.getHorarioCargoCollection().remove(horarioCargo);
                cursoCollectionCurso = em.merge(cursoCollectionCurso);
            }
            em.remove(horarioCargo);
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

    public List<HorarioCargo> findHorarioCargoEntities() {
        return findHorarioCargoEntities(true, -1, -1);
    }

    public List<HorarioCargo> findHorarioCargoEntities(int maxResults, int firstResult) {
        return findHorarioCargoEntities(false, maxResults, firstResult);
    }

    private List<HorarioCargo> findHorarioCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HorarioCargo.class));
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

    public HorarioCargo findHorarioCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HorarioCargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HorarioCargo> rt = cq.from(HorarioCargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
