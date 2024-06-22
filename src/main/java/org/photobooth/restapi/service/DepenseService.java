package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class DepenseService extends Service {
    public DepenseService() {
        super();
    }
    public List<Depense> getAllDepense() throws Exception {
        return  this.getAll(Depense.class);
    }
    
    public Optional<Depense> getDepenseById(String id) throws  Exception{
        try {
            Depense depense = getNgContext().findById(id,Depense.class);
            return Optional.ofNullable(depense);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public String save(Depense depense) throws Exception {
        return (String) getNgContext().save(depense);
    }
    public void update(Depense depense) throws Exception {
        getNgContext().update(depense);
    }
    public void delete(String id) throws Exception {
        getNgContext().delete(Depense.class, id);
    }
}

