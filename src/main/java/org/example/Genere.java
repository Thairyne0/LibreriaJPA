package org.example;
import javax.persistence.*;

@Entity
public class Genere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenere;
    @Column(name = "TitoloGenere")
    private String titolo;

    public Genere() {}

    public Genere(String titolo) {
        this.titolo = titolo;
    }

    public Long getIdGenere() {
        return idGenere;
    }

    public void setIdGenere(Long idGenere) {
        this.idGenere = idGenere;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
