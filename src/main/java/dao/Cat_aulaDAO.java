/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.CatAula;
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
public class Cat_aulaDAO {
     public List<CatAula> get_list_cat() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("recorridoVirtualJPAPU");
        EntityManager em = emf.createEntityManager();
        Query consulta_all = em.createNamedQuery("CatAula.findAll");
        List<CatAula> lista_cat = consulta_all.getResultList();
        em.close();
        emf.close();
        return lista_cat;

    }

    public int registrar_cat_aula(CatAula obj_cat_aula) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("recorridoVirtualJPAPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(obj_cat_aula);
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
