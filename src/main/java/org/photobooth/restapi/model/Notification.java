package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

import java.sql.Timestamp;

@Table(name = "notification")
public class Notification {
    @Primary(auto = true)
    public int id;
    private String libele;
    private String type;
    private String icon;
    private Timestamp action_date;
    @Col(isTransient = true, name = "id_mmebre", reference = "id_membre")
    private Membre membre;

    public Notification() {
        this.action_date = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Timestamp getAction_date() {
        return action_date;
    }

    public void setAction_date(Timestamp action_date) {
        this.action_date = action_date;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
}
