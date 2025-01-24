//package org.example;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//import java.util.List;
//
//
//
//@Transactional
//public class CatalogoService {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public CatalogoService(EntityManager em) {
//        this.entityManager = em;
//    }
//
//    public void aggiungiElemento(Elemento elemento) {
//        entityManager.persist(elemento);
//    }
//
//    public void rimuoviElemento(String codiceISBN) {
//        Elemento elemento = entityManager.find(Elemento.class, codiceISBN);
//        if (elemento != null) {
//            entityManager.remove(elemento);
//        }
//    }
//
//    public Elemento ricercaPerISBN(String codiceISBN) {
//        return entityManager.find(Elemento.class, codiceISBN);
//    }
//
//    public List<Elemento> ricercaPerAnnoPubblicazione(int anno) {
//        return entityManager.createQuery("SELECT e FROM Elemento e WHERE e.annoPubblicazione = :anno", Elemento.class)
//                .setParameter("anno", anno)
//                .getResultList();
//    }
//
//    public List<Elemento> ricercaPerAutore(String nomeAutore) {
//        return entityManager.createQuery("SELECT l FROM Libro l JOIN l.autore a WHERE a.nome = :nomeAutore", Elemento.class)
//                .setParameter("nomeAutore", nomeAutore)
//                .getResultList();
//    }
//
//    public List<Elemento> ricercaPerTitolo(String titolo) {
//        return entityManager.createQuery("SELECT e FROM Elemento e WHERE e.titolo LIKE :titolo", Elemento.class)
//                .setParameter("titolo", "%" + titolo + "%")
//                .getResultList();
//    }
//
//    public List<Prestito> ricercaPrestitiUtente(Long numeroTessera) {
//        return entityManager.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera", Prestito.class)
//                .setParameter("numeroTessera", numeroTessera)
//                .getResultList();
//    }
//
//    public List<Prestito> ricercaPrestitiScaduti() {
//        return entityManager.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < CURRENT_DATE", Prestito.class)
//                .getResultList();
//    }
//}
