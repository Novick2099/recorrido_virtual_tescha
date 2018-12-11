/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.Carrera;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CarreraDao {
    
    
    
    public List<Carrera> get_lista_carrera() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("recorridoVirtualJPAPU");
        EntityManager em = emf.createEntityManager();
        Query consulta_all = em.createNamedQuery("Carrera.findAll");
        List<Carrera> lista_carrera = consulta_all.getResultList();
        em.close();
        emf.close();
        return lista_carrera;
    }

    public int registrar_carrera(String carrera) {
        Carrera obj_carrera = new Carrera();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("recorridoVirtualJPAPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        obj_carrera.setNombreCarrera(carrera);

        tx.begin();
        try {
            em.persist(obj_carrera);
            tx.commit();
            return 1;
        } catch (Exception e) {

            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
        return 0;
    }
}
