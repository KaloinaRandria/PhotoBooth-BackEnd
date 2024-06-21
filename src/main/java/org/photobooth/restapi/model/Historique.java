package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.sql.Timestamp;

@Table(name = "historique")
public class Historique {
    @Primary(isSequence = true, sequenceName = "historique_seq", prefixe = "H_")
    private String id_historique;
    @Col(name = "id_theme", reference = "id_theme")
    private Theme theme;
    private Date date_action;
    private Timestamp date_debut;
    private Timestamp date_fin;
    private double montant_entrant;

    public Historique() {}

    public String getId_historique() {
        return id_historique;
    }

    public void setId_historique(String id_historique) {
        this.id_historique = id_historique;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Date getDate_action() {
        return date_action;
    }

    public void setDate_action(Date date_action) {
        this.date_action = date_action;
    }

    public Timestamp getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Timestamp date_debut) {
        this.date_debut = date_debut;
    }

    public Timestamp getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Timestamp date_fin) {
        this.date_fin = date_fin;
    }

    public double getMontant_entrant() {
        return montant_entrant;
    }

    public void setMontant_entrant(double montant_entrant) {
        this.montant_entrant = montant_entrant;
    }
}
