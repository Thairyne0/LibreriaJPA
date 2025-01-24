package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RivistaDAO {
    private EntityManager em;

    public RivistaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Rivista rivista) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(rivista);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Rivista findByISBN(String isbn) {
        return em.find(Rivista.class, isbn);
    }

    public List<Rivista> findAll() {
        return em.createQuery("SELECT r FROM Rivista r", Rivista.class).getResultList();
    }

    public void update(Rivista rivista) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(rivista);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(String isbn) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Rivista rivista = em.find(Rivista.class, isbn);
            if (rivista != null) {
                em.remove(rivista);
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
