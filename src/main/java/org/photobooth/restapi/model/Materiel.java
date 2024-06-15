package org.photobooth.restapi.model;

import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

@Table(name = "materiel")
public class Materiel {
    @Primary(isSequence = true, sequenceName = "materiel_seq", prefixe = "MAT_")
    private String id_materiel;
    private int quantite;
    private String intitule;
    private double prix;
    private String image_url;

    public Materiel() {}

    public String getId_materiel() {
        return id_materiel;
    }

    public void setId_materiel(String id_materiel) {
        this.id_materiel = id_materiel;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
