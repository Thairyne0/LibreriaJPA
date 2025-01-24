package org.example;
import javax.persistence.*;

@Entity
public class Libro extends Elemento {
    @ManyToOne
    @JoinColumn(name = "IDAutore")
    private Autore autore;

    @ManyToOne
    @JoinColumn(name = "IDGenere")
    private Genere genere;

    public Autore getAutore() {
        return autore;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }
}