package org.photobooth.restapi.model;


import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

import java.sql.Date;
import java.sql.Timestamp;

@Table(name = "reservation")
public class Reservation {
   @Primary(isSequence = true, sequenceName = "reservation_seq", prefixe = "RES_")
   private String id_reservation;
   private Timestamp date_reservation;
   private Date date_reservee;
   @Col(name = "id_client", reference = "id_client")
   private Client client;
   @Col(name = "id_service", reference = "id_comp_service")
   private ServComp service;
   private Timestamp heure_debut;
   private Timestamp heure_fin;
   private double prix;
   @Col(name = "id_theme", reference = "id_theme")
   private Theme theme;
   @Col(name = "id_salle", reference = "id_salle")
   private Salle salle;
   private int nb_personne;
   private boolean photograph;
   private boolean isConfirmed;
   private boolean isValid;

   public Reservation() {}

    public String getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(String id_reservation) {
        this.id_reservation = id_reservation;
    }

    public Timestamp getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Timestamp date_reservation) {
        this.date_reservation = date_reservation;
    }

    public Date getDate_reservee() {
        return date_reservee;
    }

    public void setDate_reservee(Date date_reservee) {
        this.date_reservee = date_reservee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ServComp getService() {
        return service;
    }

    public void setService(ServComp service) {
        this.service = service;
    }

    public Timestamp getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(Timestamp heure_debut) {
        this.heure_debut = heure_debut;
    }

    public Timestamp getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(Timestamp heure_fin) {
        this.heure_fin = heure_fin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public int getNb_personne() {
        return nb_personne;
    }

    public void setNb_personne(int nb_personne) {
        this.nb_personne = nb_personne;
    }

    public boolean isPhotograph() {
        return photograph;
    }

    public void setPhotograph(boolean photograph) {
        this.photograph = photograph;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
