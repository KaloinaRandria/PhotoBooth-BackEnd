package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;
import org.photobooth.restapi.model.stat.ServCompTarif;

import java.util.List;

@Table(name = "comp_service")
public class ServComp {
    @Primary(isSequence = true, sequenceName = "comp_service_seq", prefixe = "SERV_")
    private String id_comp_service;
    private String intitule;
    private double prix_unitaire;
    private String icon;
    private String color;
    @Col(isTransient = true)
    private List<ServCompTarif> tarif;


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<ServCompTarif> getTarif() {
        return tarif;
    }

    public void setTarif(List<ServCompTarif> tarif) {
        this.tarif = tarif;
    }
}
