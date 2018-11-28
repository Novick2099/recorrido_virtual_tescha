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
import entidades.Carrera;
import entidades.Materia;
import entidades.Curso;
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
public class RubricaJpaController implements Serializable {

    public RubricaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rubrica rubrica) throws RollbackFailureException, Exception {
        if (rubrica.getCursoCollection() == null) {
            rubrica.setCursoCollection(new ArrayList<Curso>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Carrera fkCarrera = rubrica.getFkCarrera();
            if (fkCarrera != null) {
                fkCarrera = em.getReference(fkCarrera.getClass(), fkCarrera.getIdCarrera());
                rubrica.setFkCarrera(fkCarrera);
            }
            Materia fkMateria = rubrica.getFkMateria();
            if (fkMateria != null) {
                fkMateria = em.getReference(fkMateria.getClass(), fkMateria.getClaveMateria());
                rubrica.setFkMateria(fkMateria);
            }
            Collection<Curso> attachedCursoCollection = new ArrayList<Curso>();
            for (Curso cursoCollectionCursoToAttach : rubrica.getCursoCollection()) {
                cursoCollectionCursoToAttach = em.getReference(cursoCollectionCursoToAttach.getClass(), cursoCollectionCursoToAttach.getIdCurso());
                attachedCursoCollection.add(cursoCollectionCursoToAttach);
            }
            rubrica.setCursoCollection(attachedCursoCollection);
            em.persist(rubrica);
            if (fkCarrera != null) {
                fkCarrera.getRubricaCollection().add(rubrica);
                fkCarrera = em.merge(fkCarrera);
            }
            if (fkMateria != null) {
                fkMateria.getRubricaCollection().add(rubrica);
                fkMateria = em.merge(fkMateria);
            }
            for (Curso cursoCollectionCurso : rubrica.getCursoCollection()) {
                Rubrica oldFkRubricaOfCursoCollectionCurso = cursoCollectionCurso.getFkRubrica();
                cursoCollectionCurso.setFkRubrica(rubrica);
                cursoCollectionCurso = em.merge(cursoCollectionCurso);
                if (oldFkRubricaOfCursoCollectionCurso != null) {
                    oldFkRubricaOfCursoCollectionCurso.getCursoCollection().remove(cursoCollectionCurso);
                    oldFkRubricaOfCursoCollectionCurso = em.merge(oldFkRubricaOfCursoCollectionCurso);
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

    public void edit(Rubrica rubrica) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Rubrica persistentRubrica = em.find(Rubrica.class, rubrica.getIdRubrica());
            Carrera fkCarreraOld = persistentRubrica.getFkCarrera();
            Carrera fkCarreraNew = rubrica.getFkCarrera();
            Materia fkMateriaOld = persistentRubrica.getFkMateria();
            Materia fkMateriaNew = rubrica.getFkMateria();
            Collection<Curso> cursoCollectionOld = persistentRubrica.getCursoCollection();
            Collection<Curso> cursoCollectionNew = rubrica.getCursoCollection();
            if (fkCarreraNew != null) {
                fkCarreraNew = em.getReference(fkCarreraNew.getClass(), fkCarreraNew.getIdCarrera());
                rubrica.setFkCarrera(fkCarreraNew);
            }
            if (fkMateriaNew != null) {
                fkMateriaNew = em.getReference(fkMateriaNew.getClass(), fkMateriaNew.getClaveMateria());
                rubrica.setFkMateria(fkMateriaNew);
            }
            Collection<Curso> attachedCursoCollectionNew = new ArrayList<Curso>();
            for (Curso cursoCollectionNewCursoToAttach : cursoCollectionNew) {
                cursoCollectionNewCursoToAttach = em.getReference(cursoCollectionNewCursoToAttach.getClass(), cursoCollectionNewCursoToAttach.getIdCurso());
                attachedCursoCollectionNew.add(cursoCollectionNewCursoToAttach);
            }
            cursoCollectionNew = attachedCursoCollectionNew;
            rubrica.setCursoCollection(cursoCollectionNew);
            rubrica = em.merge(rubrica);
            if (fkCarreraOld != null && !fkCarreraOld.equals(fkCarreraNew)) {
                fkCarreraOld.getRubricaCollection().remove(rubrica);
                fkCarreraOld = em.merge(fkCarreraOld);
            }
            if (fkCarreraNew != null && !fkCarreraNew.equals(fkCarreraOld)) {
                fkCarreraNew.getRubricaCollection().add(rubrica);
                fkCarreraNew = em.merge(fkCarreraNew);
            }
            if (fkMateriaOld != null && !fkMateriaOld.equals(fkMateriaNew)) {
                fkMateriaOld.getRubricaCollection().remove(rubrica);
                fkMateriaOld = em.merge(fkMateriaOld);
            }
            if (fkMateriaNew != null && !fkMateriaNew.equals(fkMateriaOld)) {
                fkMateriaNew.getRubricaCollection().add(rubrica);
                fkMateriaNew = em.merge(fkMateriaNew);
            }
            for (Curso cursoCollectionOldCurso : cursoCollectionOld) {
                if (!cursoCollectionNew.contains(cursoCollectionOldCurso)) {
                    cursoCollectionOldCurso.setFkRubrica(null);
                    cursoCollectionOldCurso = em.merge(cursoCollectionOldCurso);
                }
            }
            for (Curso cursoCollectionNewCurso : cursoCollectionNew) {
                if (!cursoCollectionOld.contains(cursoCollectionNewCurso)) {
                    Rubrica oldFkRubricaOfCursoCollectionNewCurso = cursoCollectionNewCurso.getFkRubrica();
                    cursoCollectionNewCurso.setFkRubrica(rubrica);
                    cursoCollectionNewCurso = em.merge(cursoCollectionNewCurso);
                    if (oldFkRubricaOfCursoCollectionNewCurso != null && !oldFkRubricaOfCursoCollectionNewCurso.equals(rubrica)) {
                        oldFkRubricaOfCursoCollectionNewCurso.getCursoCollection().remove(cursoCollectionNewCurso);
                        oldFkRubricaOfCursoCollectionNewCurso = em.merge(oldFkRubricaOfCursoCollectionNewCurso);
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
                Integer id = rubrica.getIdRubrica();
                if (findRubrica(id) == null) {
                    throw new NonexistentEntityException("The rubrica with id " + id + " no longer exists.");
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
            Rubrica rubrica;
            try {
                rubrica = em.getReference(Rubrica.class, id);
                rubrica.getIdRubrica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rubrica with id " + id + " no longer exists.", enfe);
            }
            Carrera fkCarrera = rubrica.getFkCarrera();
            if (fkCarrera != null) {
                fkCarrera.getRubricaCollection().remove(rubrica);
                fkCarrera = em.merge(fkCarrera);
            }
            Materia fkMateria = rubrica.getFkMateria();
            if (fkMateria != null) {
                fkMateria.getRubricaCollection().remove(rubrica);
                fkMateria = em.merge(fkMateria);
            }
            Collection<Curso> cursoCollection = rubrica.getCursoCollection();
            for (Curso cursoCollectionCurso : cursoCollection) {
                cursoCollectionCurso.setFkRubrica(null);
                cursoCollectionCurso = em.merge(cursoCollectionCurso);
            }
            em.remove(rubrica);
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

    public List<Rubrica> findRubricaEntities() {
        return findRubricaEntities(true, -1, -1);
    }

    public List<Rubrica> findRubricaEntities(int maxResults, int firstResult) {
        return findRubricaEntities(false, maxResults, firstResult);
    }

    private List<Rubrica> findRubricaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rubrica.class));
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

    public Rubrica findRubrica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rubrica.class, id);
        } finally {
            em.close();
        }
    }

    public int getRubricaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rubrica> rt = cq.from(Rubrica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
