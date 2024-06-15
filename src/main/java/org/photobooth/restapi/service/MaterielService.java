package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.Materiel;
import org.photobooth.restapi.model.Role;

import java.util.List;
import java.util.Optional;

public class MaterielService extends Service {
    public MaterielService() {
        super();
    }

    public List<Materiel> getAllMateriel() throws Exception {
        return this.getAll(Materiel.class);
    }

    public Optional<Materiel> getMaterielById(String id) throws Exception {
        try {
            Materiel materiel = getNgContext().findById(id, Materiel.class);
            return Optional.ofNullable(materiel);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public String save(Materiel materiel) throws Exception {
        materiel.setImage_url("");
        return (String) getNgContext().save(materiel);
    }

    public void update(Materiel materiel) throws Exception {
        getNgContext().update(materiel);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Materiel.class, id);
    }
}
