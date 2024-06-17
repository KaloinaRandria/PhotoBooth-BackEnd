package org.photobooth.restapi.model;


import org.entityframework.tools.Table;

import java.sql.Date;
import java.sql.Timestamp;

@Table(name = "reservation")
public class Reservation {

   private String id_reservation;
   private Date date_reservation;
   private Date date_reservee;
   private String id_client;
   private String id_service;
   private Timestamp heure_debut;
   private Timestamp heure_fin;
   private double prix;


    public String getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(String id_reservation) {
        this.id_reservation = id_reservation;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public Date getDate_reservee() {
        return date_reservee;
    }

    public void setDate_reservee(Date date_reservee) {
        this.date_reservee = date_reservee;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getId_service() {
        return id_service;
    }

    public void setId_service(String id_service) {
        this.id_service = id_service;
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
}
