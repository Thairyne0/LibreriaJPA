package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GenereDAO {
    private EntityManager em;

    public GenereDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Genere genere) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(genere);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Genere findById(Long id) {
        return em.find(Genere.class, id);
    }

    public List<Genere> findAll() {
        return em.createQuery("SELECT g FROM Genere g", Genere.class).getResultList();
    }

    public void update(Genere genere) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(genere);
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
            Genere genere = em.find(Genere.class, id);
            if (genere != null) {
                em.remove(genere);
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
