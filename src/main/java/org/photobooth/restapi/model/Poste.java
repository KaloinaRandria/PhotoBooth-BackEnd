package org.photobooth.restapi.model;

import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

@Table(name = "poste")
public class Poste {
    @Primary(isSequence = true, sequenceName = "id_poste_seq", prefixe = "POSTE_")
    private String id_poste;
    private String intitule;

    public Poste() { }

    public String getId_poste() {
        return id_poste;
    }

    public void setId_poste(String id_poste) {
        this.id_poste = id_poste;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
}
