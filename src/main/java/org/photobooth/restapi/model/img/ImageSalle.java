package org.photobooth.restapi.model.img;

import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

import java.sql.Timestamp;

@Table(name = "image_salle")
public class ImageSalle {
    @Primary(isSequence = true, sequenceName = "image_salle_seq", prefixe = "IMG_SALLE_")
    private String id_image_salle;
    private String id_salle;
    private String image_url;
    private Timestamp date_insertion;

    public ImageSalle() {}

    public String getId_image_salle() {
        return id_image_salle;
    }

    public void setId_image_salle(String id_image_salle) {
        this.id_image_salle = id_image_salle;
    }

    public String getId_salle() {
        return id_salle;
    }

    public void setId_salle(String id_salle) {
        this.id_salle = id_salle;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Timestamp getDate_insertion() {
        return date_insertion;
    }

    public void setDate_insertion(Timestamp date_insertion) {
        this.date_insertion = date_insertion;
    }
}
