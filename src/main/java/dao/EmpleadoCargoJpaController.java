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
import entidades.Cargo;
import entidades.Empleado;
import entidades.EmpleadoCargo;
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
public class EmpleadoCargoJpaController implements Serializable {

    public EmpleadoCargoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpleadoCargo empleadoCargo) throws RollbackFailureException, Exception {
        if (empleadoCargo.getHorarioCargoCollection() == null) {
            empleadoCargo.setHorarioCargoCollection(new ArrayList<HorarioCargo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cargo fkCargo = empleadoCargo.getFkCargo();
            if (fkCargo != null) {
                fkCargo = em.getReference(fkCargo.getClass(), fkCargo.getIdCargo());
                empleadoCargo.setFkCargo(fkCargo);
            }
            Empleado fkMatricula = empleadoCargo.getFkMatricula();
            if (fkMatricula != null) {
                fkMatricula = em.getReference(fkMatricula.getClass(), fkMatricula.getMatricula());
                empleadoCargo.setFkMatricula(fkMatricula);
            }
            Collection<HorarioCargo> attachedHorarioCargoCollection = new ArrayList<HorarioCargo>();
            for (HorarioCargo horarioCargoCollectionHorarioCargoToAttach : empleadoCargo.getHorarioCargoCollection()) {
                horarioCargoCollectionHorarioCargoToAttach = em.getReference(horarioCargoCollectionHorarioCargoToAttach.getClass(), horarioCargoCollectionHorarioCargoToAttach.getIdHorarioCargo());
                attachedHorarioCargoCollection.add(horarioCargoCollectionHorarioCargoToAttach);
            }
            empleadoCargo.setHorarioCargoCollection(attachedHorarioCargoCollection);
            em.persist(empleadoCargo);
            if (fkCargo != null) {
                fkCargo.getEmpleadoCargoCollection().add(empleadoCargo);
                fkCargo = em.merge(fkCargo);
            }
            if (fkMatricula != null) {
                fkMatricula.getEmpleadoCargoCollection().add(empleadoCargo);
                fkMatricula = em.merge(fkMatricula);
            }
            for (HorarioCargo horarioCargoCollectionHorarioCargo : empleadoCargo.getHorarioCargoCollection()) {
                EmpleadoCargo oldFkEmpleadoCargoOfHorarioCargoCollectionHorarioCargo = horarioCargoCollectionHorarioCargo.getFkEmpleadoCargo();
                horarioCargoCollectionHorarioCargo.setFkEmpleadoCargo(empleadoCargo);
                horarioCargoCollectionHorarioCargo = em.merge(horarioCargoCollectionHorarioCargo);
                if (oldFkEmpleadoCargoOfHorarioCargoCollectionHorarioCargo != null) {
                    oldFkEmpleadoCargoOfHorarioCargoCollectionHorarioCargo.getHorarioCargoCollection().remove(horarioCargoCollectionHorarioCargo);
                    oldFkEmpleadoCargoOfHorarioCargoCollectionHorarioCargo = em.merge(oldFkEmpleadoCargoOfHorarioCargoCollectionHorarioCargo);
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

    public void edit(EmpleadoCargo empleadoCargo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EmpleadoCargo persistentEmpleadoCargo = em.find(EmpleadoCargo.class, empleadoCargo.getIdEmpleadoCargo());
            Cargo fkCargoOld = persistentEmpleadoCargo.getFkCargo();
            Cargo fkCargoNew = empleadoCargo.getFkCargo();
            Empleado fkMatriculaOld = persistentEmpleadoCargo.getFkMatricula();
            Empleado fkMatriculaNew = empleadoCargo.getFkMatricula();
            Collection<HorarioCargo> horarioCargoCollectionOld = persistentEmpleadoCargo.getHorarioCargoCollection();
            Collection<HorarioCargo> horarioCargoCollectionNew = empleadoCargo.getHorarioCargoCollection();
            if (fkCargoNew != null) {
                fkCargoNew = em.getReference(fkCargoNew.getClass(), fkCargoNew.getIdCargo());
                empleadoCargo.setFkCargo(fkCargoNew);
            }
            if (fkMatriculaNew != null) {
                fkMatriculaNew = em.getReference(fkMatriculaNew.getClass(), fkMatriculaNew.getMatricula());
                empleadoCargo.setFkMatricula(fkMatriculaNew);
            }
            Collection<HorarioCargo> attachedHorarioCargoCollectionNew = new ArrayList<HorarioCargo>();
            for (HorarioCargo horarioCargoCollectionNewHorarioCargoToAttach : horarioCargoCollectionNew) {
                horarioCargoCollectionNewHorarioCargoToAttach = em.getReference(horarioCargoCollectionNewHorarioCargoToAttach.getClass(), horarioCargoCollectionNewHorarioCargoToAttach.getIdHorarioCargo());
                attachedHorarioCargoCollectionNew.add(horarioCargoCollectionNewHorarioCargoToAttach);
            }
            horarioCargoCollectionNew = attachedHorarioCargoCollectionNew;
            empleadoCargo.setHorarioCargoCollection(horarioCargoCollectionNew);
            empleadoCargo = em.merge(empleadoCargo);
            if (fkCargoOld != null && !fkCargoOld.equals(fkCargoNew)) {
                fkCargoOld.getEmpleadoCargoCollection().remove(empleadoCargo);
                fkCargoOld = em.merge(fkCargoOld);
            }
            if (fkCargoNew != null && !fkCargoNew.equals(fkCargoOld)) {
                fkCargoNew.getEmpleadoCargoCollection().add(empleadoCargo);
                fkCargoNew = em.merge(fkCargoNew);
            }
            if (fkMatriculaOld != null && !fkMatriculaOld.equals(fkMatriculaNew)) {
                fkMatriculaOld.getEmpleadoCargoCollection().remove(empleadoCargo);
                fkMatriculaOld = em.merge(fkMatriculaOld);
            }
            if (fkMatriculaNew != null && !fkMatriculaNew.equals(fkMatriculaOld)) {
                fkMatriculaNew.getEmpleadoCargoCollection().add(empleadoCargo);
                fkMatriculaNew = em.merge(fkMatriculaNew);
            }
            for (HorarioCargo horarioCargoCollectionOldHorarioCargo : horarioCargoCollectionOld) {
                if (!horarioCargoCollectionNew.contains(horarioCargoCollectionOldHorarioCargo)) {
                    horarioCargoCollectionOldHorarioCargo.setFkEmpleadoCargo(null);
                    horarioCargoCollectionOldHorarioCargo = em.merge(horarioCargoCollectionOldHorarioCargo);
                }
            }
            for (HorarioCargo horarioCargoCollectionNewHorarioCargo : horarioCargoCollectionNew) {
                if (!horarioCargoCollectionOld.contains(horarioCargoCollectionNewHorarioCargo)) {
                    EmpleadoCargo oldFkEmpleadoCargoOfHorarioCargoCollectionNewHorarioCargo = horarioCargoCollectionNewHorarioCargo.getFkEmpleadoCargo();
                    horarioCargoCollectionNewHorarioCargo.setFkEmpleadoCargo(empleadoCargo);
                    horarioCargoCollectionNewHorarioCargo = em.merge(horarioCargoCollectionNewHorarioCargo);
                    if (oldFkEmpleadoCargoOfHorarioCargoCollectionNewHorarioCargo != null && !oldFkEmpleadoCargoOfHorarioCargoCollectionNewHorarioCargo.equals(empleadoCargo)) {
                        oldFkEmpleadoCargoOfHorarioCargoCollectionNewHorarioCargo.getHorarioCargoCollection().remove(horarioCargoCollectionNewHorarioCargo);
                        oldFkEmpleadoCargoOfHorarioCargoCollectionNewHorarioCargo = em.merge(oldFkEmpleadoCargoOfHorarioCargoCollectionNewHorarioCargo);
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
                Integer id = empleadoCargo.getIdEmpleadoCargo();
                if (findEmpleadoCargo(id) == null) {
                    throw new NonexistentEntityException("The empleadoCargo with id " + id + " no longer exists.");
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
            EmpleadoCargo empleadoCargo;
            try {
                empleadoCargo = em.getReference(EmpleadoCargo.class, id);
                empleadoCargo.getIdEmpleadoCargo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleadoCargo with id " + id + " no longer exists.", enfe);
            }
            Cargo fkCargo = empleadoCargo.getFkCargo();
            if (fkCargo != null) {
                fkCargo.getEmpleadoCargoCollection().remove(empleadoCargo);
                fkCargo = em.merge(fkCargo);
            }
            Empleado fkMatricula = empleadoCargo.getFkMatricula();
            if (fkMatricula != null) {
                fkMatricula.getEmpleadoCargoCollection().remove(empleadoCargo);
                fkMatricula = em.merge(fkMatricula);
            }
            Collection<HorarioCargo> horarioCargoCollection = empleadoCargo.getHorarioCargoCollection();
            for (HorarioCargo horarioCargoCollectionHorarioCargo : horarioCargoCollection) {
                horarioCargoCollectionHorarioCargo.setFkEmpleadoCargo(null);
                horarioCargoCollectionHorarioCargo = em.merge(horarioCargoCollectionHorarioCargo);
            }
            em.remove(empleadoCargo);
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

    public List<EmpleadoCargo> findEmpleadoCargoEntities() {
        return findEmpleadoCargoEntities(true, -1, -1);
    }

    public List<EmpleadoCargo> findEmpleadoCargoEntities(int maxResults, int firstResult) {
        return findEmpleadoCargoEntities(false, maxResults, firstResult);
    }

    private List<EmpleadoCargo> findEmpleadoCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpleadoCargo.class));
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

    public EmpleadoCargo findEmpleadoCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpleadoCargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpleadoCargo> rt = cq.from(EmpleadoCargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
