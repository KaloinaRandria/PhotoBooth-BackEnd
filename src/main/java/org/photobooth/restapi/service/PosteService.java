package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.Poste;
import org.photobooth.restapi.model.Role;

import java.util.List;
import java.util.Optional;

public class PosteService extends Service {
    public PosteService() {
        super();
    }

    public List<Poste> getAllPoste() throws Exception {
        return getNgContext().findAll(Poste.class);
    }

    public Optional<Poste> getPosteById(String id) throws Exception {
        try {
            Poste poste = getNgContext().findById(id, Poste.class);
            return Optional.ofNullable(poste);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public String save(Poste poste) throws Exception {
        return (String) getNgContext().save(poste);
    }

    public void update(Poste poste) throws Exception {
        getNgContext().update(poste);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Poste.class, id);
    }
}
