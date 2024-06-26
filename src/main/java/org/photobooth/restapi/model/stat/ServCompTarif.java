package org.photobooth.restapi.model.stat;

public class ServCompTarif {
    private String range_label;
    private double prix;
    private String id_comp_service;

    public ServCompTarif() {}

    public String getRange_label() {
        return range_label;
    }

    public void setRange_label(String range_label) {
        this.range_label = range_label;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getId_comp_service() {
        return id_comp_service;
    }

    public void setId_comp_service(String id_comp_service) {
        this.id_comp_service = id_comp_service;
    }
}
