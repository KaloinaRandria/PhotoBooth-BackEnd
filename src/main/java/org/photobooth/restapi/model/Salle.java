package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;
import org.photobooth.restapi.model.img.ImageSalle;

import java.util.List;

@Table(name = "salle")
public class Salle {
    @Primary(isSequence = true, sequenceName = "salle_seq", prefixe = "SALLE_")
    private String id_salle;
    private int numero;
    @Col(isTransient = true)
    private List<ImageSalle> imageSalle;

    public Salle() {

    }

    public String getId_salle() {
        return id_salle;
    }

    public void setId_salle(String id_salle) {
        this.id_salle = id_salle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<ImageSalle> getImageSalle() {
        return imageSalle;
    }

    public void setImageSalle(List<ImageSalle> imageSalle) {
        this.imageSalle = imageSalle;
    }
}
