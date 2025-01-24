package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;


public class AutoreDAO {
    private EntityManager em;

    public AutoreDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Autore autore) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(autore);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Autore findById(Long id) {
        return em.find(Autore.class, id);
    }

    public List<Autore> findAll() {
        return em.createQuery("SELECT a FROM Autore a", Autore.class).getResultList();
    }

    public void update(Autore autore) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(autore);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Autore autore = em.find(Autore.class, id);
            if (autore != null) {
                em.remove(autore);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
