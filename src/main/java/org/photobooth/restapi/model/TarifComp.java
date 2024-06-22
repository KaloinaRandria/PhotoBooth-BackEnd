package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

@Table(name = "tarif_comp_service")
public class TarifComp {
    @Primary(isSequence = true, sequenceName = "tarif_comp_service_seq", prefixe = "TCS_")
    private String id_tarif_service;
    private String id_comp_service;
    private int id_value_ranges;
    private double prix;

    public TarifComp() {}

    public String getId_tarif_service() {
        return id_tarif_service;
    }

    public void setId_tarif_service(String id_tarif_service) {
        this.id_tarif_service = id_tarif_service;
    }

    public String getId_comp_service() {
        return id_comp_service;
    }

    public void setId_comp_service(String id_comp_service) {
        this.id_comp_service = id_comp_service;
    }

    public int getId_value_ranges() {
        return id_value_ranges;
    }

    public void setId_value_ranges(int id_value_ranges) {
        this.id_value_ranges = id_value_ranges;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
