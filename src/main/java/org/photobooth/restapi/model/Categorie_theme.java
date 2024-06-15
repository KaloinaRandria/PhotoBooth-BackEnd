package org.photobooth.restapi.model;

import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

@Table(name = "categorie_theme")
public class Categorie_theme {
    @Primary(isSequence = true, sequenceName = "categorie_theme_seq", prefixe = "CAT_TH_")
    private String id_categorie_theme;
    private String intitule;

    public Categorie_theme() {}

    public String getId_categorie_theme() {
        return id_categorie_theme;
    }

    public void setId_categorie_theme(String id_categorie_theme) {
        this.id_categorie_theme = id_categorie_theme;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
}
