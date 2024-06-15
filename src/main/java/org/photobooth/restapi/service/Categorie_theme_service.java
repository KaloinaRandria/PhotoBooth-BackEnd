package org.photobooth.restapi.service;

import org.photobooth.restapi.model.Categorie_theme;

import java.util.List;

public class Categorie_theme_service extends Service {
    public Categorie_theme_service() {
        super();
    }

    public List<Categorie_theme> getAll() throws Exception {
        return this.getAll(Categorie_theme.class);
    }

    public String save(Categorie_theme categorie_theme) throws Exception {
        return (String) getNgContext().save(categorie_theme);
    }

    public void update(Categorie_theme categorie_theme) throws Exception {
        getNgContext().update(categorie_theme);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Categorie_theme.class, id);
    }
}
