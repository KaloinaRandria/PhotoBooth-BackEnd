package org.photobooth.restapi.model.img;

import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

import java.sql.Timestamp;

@Table(name = "image_theme")
public class ImageTheme {
    @Primary(isSequence = true, sequenceName = "image_theme_seq", prefixe = "IMG_TH_")
    private String id_image_theme;
    private String id_theme;
    private String image_url;
    private Timestamp date_insertion;

    public ImageTheme() {}

    public String getId_image_theme() {
        return id_image_theme;
    }

    public void setId_image_theme(String id_image_theme) {
        this.id_image_theme = id_image_theme;
    }

    public String getId_theme() {
        return id_theme;
    }

    public void setId_theme(String id_theme) {
        this.id_theme = id_theme;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Timestamp getDate_inserion() {
        return date_insertion;
    }

    public void setDate_inserion(Timestamp date_inserion) {
        this.date_insertion = date_inserion;
    }
}
