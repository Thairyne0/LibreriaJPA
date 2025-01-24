package org.example;
import javax.persistence.*;

@Entity
public class Rivista extends Elemento {
    private String periodicita;

    public String getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(String periodicita) {
        this.periodicita = periodicita;
    }
}
