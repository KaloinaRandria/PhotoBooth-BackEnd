package org.photobooth.restapi.model;

import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

import java.sql.Timestamp;

@Table(name = "salaire")
public class Salaire {
    @Primary(isSequence = true, sequenceName = "id_salaire_seq", prefixe = "SAL_")
    private String id_salaire;
    private String id_membre;
    private double montant;
    private Timestamp date_insertion;

    public Salaire() { }

    public String getId_salaire() {
        return id_salaire;
    }

    public void setId_salaire(String id_salaire) {
        this.id_salaire = id_salaire;
    }

    public String getId_membre() {
        return id_membre;
    }

    public void setId_membre(String id_membre) {
        this.id_membre = id_membre;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Timestamp getDate_insertion() {
        return date_insertion;
    }

    public void setDate_insertion(Timestamp date_insertion) {
        this.date_insertion = date_insertion;
    }
}
