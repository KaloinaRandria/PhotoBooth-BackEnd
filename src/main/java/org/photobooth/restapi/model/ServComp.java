package org.photobooth.restapi.model;

import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

@Table(name = "comp_service")
public class ServComp {
    @Primary(isSequence = true, sequenceName = "comp_service_seq", prefixe = "SERV_")
    private String id_comp_service;
    private String intitule;
    private double prix_unitaire;

    public ServComp() {}

    public String getId_comp_service() {
        return id_comp_service;
    }

    public void setId_comp_service(String id_comp_service) {
        this.id_comp_service = id_comp_service;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
}
