/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.Materia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author MarzoNegro
 */
public class MateriaDAO {
    
    
    public List<Materia> get_lista_materia(){
        
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("recorridoVirtualJPAPU");
        EntityManager em=emf.createEntityManager();
        Query consulta_all = em.createNamedQuery("Materia.findAll");
        List<Materia> lista_materia=consulta_all.getResultList();
        em.close();
        emf.close();
        return lista_materia;
    }
    
    public int registrar_materia(Materia obj_Materia){
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("recorridoVirtualJPAPU");
        EntityManager em=emf.createEntityManager();
        EntityTransaction tx= em.getTransaction();
        tx.begin();
        try{
            em.persist(obj_Materia);
            tx.commit();
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
            emf.close();
        }
        return 0;
    }
}
