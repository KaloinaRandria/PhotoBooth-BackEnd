package org.photobooth.restapi.service;

import org.photobooth.restapi.model.Categorie_theme;
import org.photobooth.restapi.model.Notification;

import java.util.List;

public class Categorie_theme_service extends Service {
    public Categorie_theme_service() {
        super();
    }

    public List<Categorie_theme> getAll() throws Exception {
        return this.getAll(Categorie_theme.class);
    }

    public String save(Categorie_theme categorie_theme) throws Exception {
        String s = (String) getNgContext().save(categorie_theme);

        Notification notification = new Notification();
        notification.setLibele("New categ theme added : " + categorie_theme.getIntitule());
        notification.setType("info");
        notification.setIcon("mdi mdi-bookmark-plus-outline");
        getNgContext().save(notification);

        return s;
    }

    public void update(Categorie_theme categorie_theme) throws Exception {
        getNgContext().update(categorie_theme);

        Notification notification = new Notification();
        notification.setLibele("Update categ theme : " + categorie_theme.getId_categorie_theme());
        notification.setType("secondary");
        notification.setIcon("mdi mdi-lead-pencil");
        getNgContext().save(notification);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Categorie_theme.class, id);

        Notification notification = new Notification();
        notification.setLibele("Delete categ theme : " + id);
        notification.setType("danger");
        notification.setIcon("mdi mdi-delete-forever");
        getNgContext().save(notification);
    }
}
