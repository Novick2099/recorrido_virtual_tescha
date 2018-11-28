/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.EmpleadoCargo;
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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws RollbackFailureException, Exception {
        if (empleado.getEmpleadoCargoCollection() == null) {
            empleado.setEmpleadoCargoCollection(new ArrayList<EmpleadoCargo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<EmpleadoCargo> attachedEmpleadoCargoCollection = new ArrayList<EmpleadoCargo>();
            for (EmpleadoCargo empleadoCargoCollectionEmpleadoCargoToAttach : empleado.getEmpleadoCargoCollection()) {
                empleadoCargoCollectionEmpleadoCargoToAttach = em.getReference(empleadoCargoCollectionEmpleadoCargoToAttach.getClass(), empleadoCargoCollectionEmpleadoCargoToAttach.getIdEmpleadoCargo());
                attachedEmpleadoCargoCollection.add(empleadoCargoCollectionEmpleadoCargoToAttach);
            }
            empleado.setEmpleadoCargoCollection(attachedEmpleadoCargoCollection);
            em.persist(empleado);
            for (EmpleadoCargo empleadoCargoCollectionEmpleadoCargo : empleado.getEmpleadoCargoCollection()) {
                Empleado oldFkMatriculaOfEmpleadoCargoCollectionEmpleadoCargo = empleadoCargoCollectionEmpleadoCargo.getFkMatricula();
                empleadoCargoCollectionEmpleadoCargo.setFkMatricula(empleado);
                empleadoCargoCollectionEmpleadoCargo = em.merge(empleadoCargoCollectionEmpleadoCargo);
                if (oldFkMatriculaOfEmpleadoCargoCollectionEmpleadoCargo != null) {
                    oldFkMatriculaOfEmpleadoCargoCollectionEmpleadoCargo.getEmpleadoCargoCollection().remove(empleadoCargoCollectionEmpleadoCargo);
                    oldFkMatriculaOfEmpleadoCargoCollectionEmpleadoCargo = em.merge(oldFkMatriculaOfEmpleadoCargoCollectionEmpleadoCargo);
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

    public void edit(Empleado empleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getMatricula());
            Collection<EmpleadoCargo> empleadoCargoCollectionOld = persistentEmpleado.getEmpleadoCargoCollection();
            Collection<EmpleadoCargo> empleadoCargoCollectionNew = empleado.getEmpleadoCargoCollection();
            Collection<EmpleadoCargo> attachedEmpleadoCargoCollectionNew = new ArrayList<EmpleadoCargo>();
            for (EmpleadoCargo empleadoCargoCollectionNewEmpleadoCargoToAttach : empleadoCargoCollectionNew) {
                empleadoCargoCollectionNewEmpleadoCargoToAttach = em.getReference(empleadoCargoCollectionNewEmpleadoCargoToAttach.getClass(), empleadoCargoCollectionNewEmpleadoCargoToAttach.getIdEmpleadoCargo());
                attachedEmpleadoCargoCollectionNew.add(empleadoCargoCollectionNewEmpleadoCargoToAttach);
            }
            empleadoCargoCollectionNew = attachedEmpleadoCargoCollectionNew;
            empleado.setEmpleadoCargoCollection(empleadoCargoCollectionNew);
            empleado = em.merge(empleado);
            for (EmpleadoCargo empleadoCargoCollectionOldEmpleadoCargo : empleadoCargoCollectionOld) {
                if (!empleadoCargoCollectionNew.contains(empleadoCargoCollectionOldEmpleadoCargo)) {
                    empleadoCargoCollectionOldEmpleadoCargo.setFkMatricula(null);
                    empleadoCargoCollectionOldEmpleadoCargo = em.merge(empleadoCargoCollectionOldEmpleadoCargo);
                }
            }
            for (EmpleadoCargo empleadoCargoCollectionNewEmpleadoCargo : empleadoCargoCollectionNew) {
                if (!empleadoCargoCollectionOld.contains(empleadoCargoCollectionNewEmpleadoCargo)) {
                    Empleado oldFkMatriculaOfEmpleadoCargoCollectionNewEmpleadoCargo = empleadoCargoCollectionNewEmpleadoCargo.getFkMatricula();
                    empleadoCargoCollectionNewEmpleadoCargo.setFkMatricula(empleado);
                    empleadoCargoCollectionNewEmpleadoCargo = em.merge(empleadoCargoCollectionNewEmpleadoCargo);
                    if (oldFkMatriculaOfEmpleadoCargoCollectionNewEmpleadoCargo != null && !oldFkMatriculaOfEmpleadoCargoCollectionNewEmpleadoCargo.equals(empleado)) {
                        oldFkMatriculaOfEmpleadoCargoCollectionNewEmpleadoCargo.getEmpleadoCargoCollection().remove(empleadoCargoCollectionNewEmpleadoCargo);
                        oldFkMatriculaOfEmpleadoCargoCollectionNewEmpleadoCargo = em.merge(oldFkMatriculaOfEmpleadoCargoCollectionNewEmpleadoCargo);
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
                Integer id = empleado.getMatricula();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Collection<EmpleadoCargo> empleadoCargoCollection = empleado.getEmpleadoCargoCollection();
            for (EmpleadoCargo empleadoCargoCollectionEmpleadoCargo : empleadoCargoCollection) {
                empleadoCargoCollectionEmpleadoCargo.setFkMatricula(null);
                empleadoCargoCollectionEmpleadoCargo = em.merge(empleadoCargoCollectionEmpleadoCargo);
            }
            em.remove(empleado);
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

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
