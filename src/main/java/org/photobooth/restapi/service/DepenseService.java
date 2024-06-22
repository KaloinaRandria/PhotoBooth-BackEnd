package org.photobooth.restapi.service;

import org.photobooth.restapi.model.*;

import java.sql.Timestamp;

public class DepenseService extends Service {
    public DepenseService() {
        super();
    }

    public String save(Depense depense) throws Exception {
        return (String) getNgContext().save(depense);
    }
    public void update(Depense depense) throws Exception {
        getNgContext().update(depense);
    }
}

