package org.photobooth.restapi.service;

import org.photobooth.restapi.model.*;
import org.photobooth.restapi.model.stat.DepenseStat;

import java.sql.Timestamp;
import java.util.List;

public class DepenseService extends Service {
    public DepenseService() {
        super();
    }

    public List<Depense> getAllDepense() throws Exception {
        return  this.getAll(Depense.class);
    }

    public void save(Depense depense) throws Exception {
        getNgContext().save(depense);
    }
    public void update(Depense depense) throws Exception {
        getNgContext().update(depense);
    }

    public DepenseStat getDepense(int annee) throws Exception {
        return DepenseStat.getDepenseStat(annee, getNgContext());
    }
}

