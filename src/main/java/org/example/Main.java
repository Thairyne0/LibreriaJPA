package org.example;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotecaPU");
        EntityManager em = emf.createEntityManager();


        AutoreDAO autoreDAO = new AutoreDAO(em);
        GenereDAO genereDAO = new GenereDAO(em);
        LibroDAO libroDAO = new LibroDAO(em);
        RivistaDAO rivistaDAO = new RivistaDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        Scanner scanner = new Scanner(System.in);
        int scelta;

        do {
            System.out.println("\n--- Gestione Catalogo Bibliografico ---");
            System.out.println("1. Aggiungi un elemento al catalogo");
            System.out.println("2. Rimuovi un elemento dal catalogo");
            System.out.println("3. Ricerca elemento per ISBN");
            System.out.println("4. Ricerca elementi per anno di pubblicazione");
            System.out.println("5. Ricerca elementi per autore");
            System.out.println("6. Ricerca elementi per titolo");
            System.out.println("7. Ricerca prestiti di un utente");
            System.out.println("8. Ricerca prestiti scaduti");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");
            scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    aggiungiElemento(autoreDAO, genereDAO, libroDAO, rivistaDAO, scanner);
                    break;
                case 2:
                    rimuoviElemento(libroDAO, rivistaDAO, scanner);
                    break;
                case 3:
                    ricercaPerISBN(libroDAO, rivistaDAO, scanner);
                    break;
                case 4:
                    ricercaPerAnnoPubblicazione(libroDAO, rivistaDAO, scanner);
                    break;
                case 5:
                    ricercaPerAutore(libroDAO, scanner);
                    break;
                case 6:
                    ricercaPerTitolo(libroDAO, rivistaDAO, scanner);
                    break;
                case 7:
                    ricercaPrestitiUtente(prestitoDAO, scanner);
                    break;
                case 8:
                    ricercaPrestitiScaduti(prestitoDAO);
                    break;
                case 0:
                    System.out.println("Uscita dal programma.");
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        } while (scelta != 0);

        em.close();
        emf.close();
        scanner.close();
    }

    private static void aggiungiElemento(AutoreDAO autoreDAO, GenereDAO genereDAO, LibroDAO libroDAO, RivistaDAO rivistaDAO, Scanner scanner) {
        System.out.println("\n--- Aggiungi un elemento ---");
        System.out.print("Inserisci il tipo di elemento (1. Libro, 2. Rivista): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Inserisci il codice ISBN: ");
        String codiceISBN = scanner.nextLine();
        System.out.print("Inserisci il titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Inserisci l'anno di pubblicazione: ");
        int annoPubblicazione = scanner.nextInt();
        System.out.print("Inserisci il numero di pagine: ");
        int numeroPagine = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Inserisci l'autore: ");
            String autore = scanner.nextLine();
            System.out.print("Inserisci il genere: ");
            String genere = scanner.nextLine();


            String[] nomeCognome = autore.split(" ");
            if (nomeCognome.length >= 2) {
                Autore nuovoAutore = new Autore(nomeCognome[0], nomeCognome[1]);
                autoreDAO.save(nuovoAutore);


                Genere nuovoGenere = new Genere(genere);
                genereDAO.save(nuovoGenere);


                Libro libro = new Libro();
                libro.setCodiceISBN(codiceISBN);
                libro.setTitolo(titolo);
                libro.setAnnoPubblicazione(annoPubblicazione);
                libro.setNumeroPagine(numeroPagine);
                libro.setAutore(nuovoAutore);
                libro.setGenere(nuovoGenere);
                libroDAO.save(libro);
                System.out.println("Libro aggiunto con successo.");
            } else {
                System.out.println("Errore: il nome dell'autore deve contenere almeno uno spazio (es. 'George Orwell').");
            }
        } else if (tipo == 2) {
            System.out.print("Inserisci la periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
            String periodicita = scanner.nextLine();


            Rivista rivista = new Rivista();
            rivista.setCodiceISBN(codiceISBN);
            rivista.setTitolo(titolo);
            rivista.setAnnoPubblicazione(annoPubblicazione);
            rivista.setNumeroPagine(numeroPagine);
            rivista.setPeriodicita(periodicita);
            rivistaDAO.save(rivista);
            System.out.println("Rivista aggiunta con successo.");
        } else {
            System.out.println("Tipo non valido.");
        }
    }

    private static void rimuoviElemento(LibroDAO libroDAO, RivistaDAO rivistaDAO, Scanner scanner) {
        System.out.println("\n--- Rimuovi un elemento ---");
        System.out.print("Inserisci il codice ISBN dell'elemento da rimuovere: ");
        String codiceISBN = scanner.nextLine();


        Libro libro = libroDAO.findByISBN(codiceISBN);
        if (libro != null) {
            libroDAO.delete(codiceISBN);
            System.out.println("Libro rimosso con successo.");
            return;
        }


        Rivista rivista = rivistaDAO.findByISBN(codiceISBN);
        if (rivista != null) {
            rivistaDAO.delete(codiceISBN);
            System.out.println("Rivista rimossa con successo.");
            return;
        }

        System.out.println("Nessun elemento trovato con questo ISBN.");
    }

    private static void ricercaPerISBN(LibroDAO libroDAO, RivistaDAO rivistaDAO, Scanner scanner) {
        System.out.println("\n--- Ricerca per ISBN ---");
        System.out.print("Inserisci il codice ISBN: ");
        String codiceISBN = scanner.nextLine();

        Libro libro = libroDAO.findByISBN(codiceISBN);
        if (libro != null) {
            System.out.println("Libro trovato: " + libro.getTitolo());
            return;
        }

        Rivista rivista = rivistaDAO.findByISBN(codiceISBN);
        if (rivista != null) {
            System.out.println("Rivista trovata: " + rivista.getTitolo());
            return;
        }

        System.out.println("Nessun elemento trovato con questo ISBN.");
    }

    private static void ricercaPerAnnoPubblicazione(LibroDAO libroDAO, RivistaDAO rivistaDAO, Scanner scanner) {
        System.out.println("\n--- Ricerca per anno di pubblicazione ---");
        System.out.print("Inserisci l'anno di pubblicazione: ");
        int anno = scanner.nextInt();
        scanner.nextLine();

        List<Libro> libri = libroDAO.findAll().stream()
                .filter(l -> l.getAnnoPubblicazione() == anno)
                .toList();

        List<Rivista> riviste = rivistaDAO.findAll().stream()
                .filter(r -> r.getAnnoPubblicazione() == anno)
                .toList();

        if (libri.isEmpty() && riviste.isEmpty()) {
            System.out.println("Nessun elemento trovato per questo anno.");
        } else {
            libri.forEach(l -> System.out.println("Libro: " + l.getTitolo()));
            riviste.forEach(r -> System.out.println("Rivista: " + r.getTitolo()));
        }
    }

    private static void ricercaPerAutore(LibroDAO libroDAO, Scanner scanner) {
        System.out.println("\n--- Ricerca per autore ---");
        System.out.print("Inserisci il nome dell'autore: ");
        String autore = scanner.nextLine();


        List<Libro> libri = libroDAO.findAll().stream()
                .filter(l -> l.getAutore().getNome().equalsIgnoreCase(autore) || l.getAutore().getCognome().equalsIgnoreCase(autore))
                .toList();

        if (libri.isEmpty()) {
            System.out.println("Nessun libro trovato per questo autore.");
        } else {
            libri.forEach(l -> System.out.println("Libro: " + l.getTitolo()));
        }
    }

    private static void ricercaPerTitolo(LibroDAO libroDAO, RivistaDAO rivistaDAO, Scanner scanner) {
        System.out.println("\n--- Ricerca per titolo ---");
        System.out.print("Inserisci il titolo o parte di esso: ");
        String titolo = scanner.nextLine();


        List<Libro> libri = libroDAO.findAll().stream()
                .filter(l -> l.getTitolo().toLowerCase().contains(titolo.toLowerCase()))
                .toList();

        List<Rivista> riviste = rivistaDAO.findAll().stream()
                .filter(r -> r.getTitolo().toLowerCase().contains(titolo.toLowerCase()))
                .toList();

        if (libri.isEmpty() && riviste.isEmpty()) {
            System.out.println("Nessun elemento trovato con questo titolo.");
        } else {
            libri.forEach(l -> System.out.println("Libro: " + l.getTitolo()));
            riviste.forEach(r -> System.out.println("Rivista: " + r.getTitolo()));
        }
    }

    private static void ricercaPrestitiUtente(PrestitoDAO prestitoDAO, Scanner scanner) {
        System.out.println("\n--- Ricerca prestiti di un utente ---");
        System.out.print("Inserisci il numero di tessera dell'utente: ");
        Long numeroTessera = scanner.nextLong();
        scanner.nextLine();

        List<Prestito> prestiti = prestitoDAO.findAll().stream()
                .filter(p -> p.getUtente().getNumeroTessera().equals(numeroTessera))
                .toList();

        if (prestiti.isEmpty()) {
            System.out.println("Nessun prestito trovato per questo utente.");
        } else {
            prestiti.forEach(p -> System.out.println("Prestito: " + p.getElementoPrestato().getTitolo()));
        }
    }

    private static void ricercaPrestitiScaduti(PrestitoDAO prestitoDAO) {
        System.out.println("\n--- Ricerca prestiti scaduti ---");
        LocalDate oggi = LocalDate.now();

        List<Prestito> prestitiScaduti = prestitoDAO.findAll().stream()
                .filter(p -> p.getDataRestituzioneEffettiva() == null && p.getDataRestituzionePrevista().isBefore(oggi))
                .toList();

        if (prestitiScaduti.isEmpty()) {
            System.out.println("Nessun prestito scaduto trovato.");
        } else {
            prestitiScaduti.forEach(p -> System.out.println("Prestito scaduto: " + p.getElementoPrestato().getTitolo()));
        }
    }
}