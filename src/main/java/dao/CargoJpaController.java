/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Cargo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.EmpleadoCargo;
import java.util.ArrayList;
import java.util.Collection;
import entidades.FuncionCargo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author MarzoNegro
 */
public class CargoJpaController implements Serializable {

    public CargoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargo cargo) throws RollbackFailureException, Exception {
        if (cargo.getEmpleadoCargoCollection() == null) {
            cargo.setEmpleadoCargoCollection(new ArrayList<EmpleadoCargo>());
        }
        if (cargo.getFuncionCargoCollection() == null) {
            cargo.setFuncionCargoCollection(new ArrayList<FuncionCargo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<EmpleadoCargo> attachedEmpleadoCargoCollection = new ArrayList<EmpleadoCargo>();
            for (EmpleadoCargo empleadoCargoCollectionEmpleadoCargoToAttach : cargo.getEmpleadoCargoCollection()) {
                empleadoCargoCollectionEmpleadoCargoToAttach = em.getReference(empleadoCargoCollectionEmpleadoCargoToAttach.getClass(), empleadoCargoCollectionEmpleadoCargoToAttach.getIdEmpleadoCargo());
                attachedEmpleadoCargoCollection.add(empleadoCargoCollectionEmpleadoCargoToAttach);
            }
            cargo.setEmpleadoCargoCollection(attachedEmpleadoCargoCollection);
            Collection<FuncionCargo> attachedFuncionCargoCollection = new ArrayList<FuncionCargo>();
            for (FuncionCargo funcionCargoCollectionFuncionCargoToAttach : cargo.getFuncionCargoCollection()) {
                funcionCargoCollectionFuncionCargoToAttach = em.getReference(funcionCargoCollectionFuncionCargoToAttach.getClass(), funcionCargoCollectionFuncionCargoToAttach.getIdFuncion());
                attachedFuncionCargoCollection.add(funcionCargoCollectionFuncionCargoToAttach);
            }
            cargo.setFuncionCargoCollection(attachedFuncionCargoCollection);
            em.persist(cargo);
            for (EmpleadoCargo empleadoCargoCollectionEmpleadoCargo : cargo.getEmpleadoCargoCollection()) {
                Cargo oldFkCargoOfEmpleadoCargoCollectionEmpleadoCargo = empleadoCargoCollectionEmpleadoCargo.getFkCargo();
                empleadoCargoCollectionEmpleadoCargo.setFkCargo(cargo);
                empleadoCargoCollectionEmpleadoCargo = em.merge(empleadoCargoCollectionEmpleadoCargo);
                if (oldFkCargoOfEmpleadoCargoCollectionEmpleadoCargo != null) {
                    oldFkCargoOfEmpleadoCargoCollectionEmpleadoCargo.getEmpleadoCargoCollection().remove(empleadoCargoCollectionEmpleadoCargo);
                    oldFkCargoOfEmpleadoCargoCollectionEmpleadoCargo = em.merge(oldFkCargoOfEmpleadoCargoCollectionEmpleadoCargo);
                }
            }
            for (FuncionCargo funcionCargoCollectionFuncionCargo : cargo.getFuncionCargoCollection()) {
                Cargo oldFkCargoOfFuncionCargoCollectionFuncionCargo = funcionCargoCollectionFuncionCargo.getFkCargo();
                funcionCargoCollectionFuncionCargo.setFkCargo(cargo);
                funcionCargoCollectionFuncionCargo = em.merge(funcionCargoCollectionFuncionCargo);
                if (oldFkCargoOfFuncionCargoCollectionFuncionCargo != null) {
                    oldFkCargoOfFuncionCargoCollectionFuncionCargo.getFuncionCargoCollection().remove(funcionCargoCollectionFuncionCargo);
                    oldFkCargoOfFuncionCargoCollectionFuncionCargo = em.merge(oldFkCargoOfFuncionCargoCollectionFuncionCargo);
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

    public void edit(Cargo cargo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getIdCargo());
            Collection<EmpleadoCargo> empleadoCargoCollectionOld = persistentCargo.getEmpleadoCargoCollection();
            Collection<EmpleadoCargo> empleadoCargoCollectionNew = cargo.getEmpleadoCargoCollection();
            Collection<FuncionCargo> funcionCargoCollectionOld = persistentCargo.getFuncionCargoCollection();
            Collection<FuncionCargo> funcionCargoCollectionNew = cargo.getFuncionCargoCollection();
            Collection<EmpleadoCargo> attachedEmpleadoCargoCollectionNew = new ArrayList<EmpleadoCargo>();
            for (EmpleadoCargo empleadoCargoCollectionNewEmpleadoCargoToAttach : empleadoCargoCollectionNew) {
                empleadoCargoCollectionNewEmpleadoCargoToAttach = em.getReference(empleadoCargoCollectionNewEmpleadoCargoToAttach.getClass(), empleadoCargoCollectionNewEmpleadoCargoToAttach.getIdEmpleadoCargo());
                attachedEmpleadoCargoCollectionNew.add(empleadoCargoCollectionNewEmpleadoCargoToAttach);
            }
            empleadoCargoCollectionNew = attachedEmpleadoCargoCollectionNew;
            cargo.setEmpleadoCargoCollection(empleadoCargoCollectionNew);
            Collection<FuncionCargo> attachedFuncionCargoCollectionNew = new ArrayList<FuncionCargo>();
            for (FuncionCargo funcionCargoCollectionNewFuncionCargoToAttach : funcionCargoCollectionNew) {
                funcionCargoCollectionNewFuncionCargoToAttach = em.getReference(funcionCargoCollectionNewFuncionCargoToAttach.getClass(), funcionCargoCollectionNewFuncionCargoToAttach.getIdFuncion());
                attachedFuncionCargoCollectionNew.add(funcionCargoCollectionNewFuncionCargoToAttach);
            }
            funcionCargoCollectionNew = attachedFuncionCargoCollectionNew;
            cargo.setFuncionCargoCollection(funcionCargoCollectionNew);
            cargo = em.merge(cargo);
            for (EmpleadoCargo empleadoCargoCollectionOldEmpleadoCargo : empleadoCargoCollectionOld) {
                if (!empleadoCargoCollectionNew.contains(empleadoCargoCollectionOldEmpleadoCargo)) {
                    empleadoCargoCollectionOldEmpleadoCargo.setFkCargo(null);
                    empleadoCargoCollectionOldEmpleadoCargo = em.merge(empleadoCargoCollectionOldEmpleadoCargo);
                }
            }
            for (EmpleadoCargo empleadoCargoCollectionNewEmpleadoCargo : empleadoCargoCollectionNew) {
                if (!empleadoCargoCollectionOld.contains(empleadoCargoCollectionNewEmpleadoCargo)) {
                    Cargo oldFkCargoOfEmpleadoCargoCollectionNewEmpleadoCargo = empleadoCargoCollectionNewEmpleadoCargo.getFkCargo();
                    empleadoCargoCollectionNewEmpleadoCargo.setFkCargo(cargo);
                    empleadoCargoCollectionNewEmpleadoCargo = em.merge(empleadoCargoCollectionNewEmpleadoCargo);
                    if (oldFkCargoOfEmpleadoCargoCollectionNewEmpleadoCargo != null && !oldFkCargoOfEmpleadoCargoCollectionNewEmpleadoCargo.equals(cargo)) {
                        oldFkCargoOfEmpleadoCargoCollectionNewEmpleadoCargo.getEmpleadoCargoCollection().remove(empleadoCargoCollectionNewEmpleadoCargo);
                        oldFkCargoOfEmpleadoCargoCollectionNewEmpleadoCargo = em.merge(oldFkCargoOfEmpleadoCargoCollectionNewEmpleadoCargo);
                    }
                }
            }
            for (FuncionCargo funcionCargoCollectionOldFuncionCargo : funcionCargoCollectionOld) {
                if (!funcionCargoCollectionNew.contains(funcionCargoCollectionOldFuncionCargo)) {
                    funcionCargoCollectionOldFuncionCargo.setFkCargo(null);
                    funcionCargoCollectionOldFuncionCargo = em.merge(funcionCargoCollectionOldFuncionCargo);
                }
            }
            for (FuncionCargo funcionCargoCollectionNewFuncionCargo : funcionCargoCollectionNew) {
                if (!funcionCargoCollectionOld.contains(funcionCargoCollectionNewFuncionCargo)) {
                    Cargo oldFkCargoOfFuncionCargoCollectionNewFuncionCargo = funcionCargoCollectionNewFuncionCargo.getFkCargo();
                    funcionCargoCollectionNewFuncionCargo.setFkCargo(cargo);
                    funcionCargoCollectionNewFuncionCargo = em.merge(funcionCargoCollectionNewFuncionCargo);
                    if (oldFkCargoOfFuncionCargoCollectionNewFuncionCargo != null && !oldFkCargoOfFuncionCargoCollectionNewFuncionCargo.equals(cargo)) {
                        oldFkCargoOfFuncionCargoCollectionNewFuncionCargo.getFuncionCargoCollection().remove(funcionCargoCollectionNewFuncionCargo);
                        oldFkCargoOfFuncionCargoCollectionNewFuncionCargo = em.merge(oldFkCargoOfFuncionCargoCollectionNewFuncionCargo);
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
                Integer id = cargo.getIdCargo();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
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
            Cargo cargo;
            try {
                cargo = em.getReference(Cargo.class, id);
                cargo.getIdCargo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.", enfe);
            }
            Collection<EmpleadoCargo> empleadoCargoCollection = cargo.getEmpleadoCargoCollection();
            for (EmpleadoCargo empleadoCargoCollectionEmpleadoCargo : empleadoCargoCollection) {
                empleadoCargoCollectionEmpleadoCargo.setFkCargo(null);
                empleadoCargoCollectionEmpleadoCargo = em.merge(empleadoCargoCollectionEmpleadoCargo);
            }
            Collection<FuncionCargo> funcionCargoCollection = cargo.getFuncionCargoCollection();
            for (FuncionCargo funcionCargoCollectionFuncionCargo : funcionCargoCollection) {
                funcionCargoCollectionFuncionCargo.setFkCargo(null);
                funcionCargoCollectionFuncionCargo = em.merge(funcionCargoCollectionFuncionCargo);
            }
            em.remove(cargo);
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

    public List<Cargo> findCargoEntities() {
        return findCargoEntities(true, -1, -1);
    }

    public List<Cargo> findCargoEntities(int maxResults, int firstResult) {
        return findCargoEntities(false, maxResults, firstResult);
    }

    private List<Cargo> findCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargo.class));
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

    public Cargo findCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargo> rt = cq.from(Cargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
