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

        Notification notification = new Notification();
        notification.setLibele("New expense added : " + depense.getMontant());
        notification.setType("warning");
        notification.setIcon("mdi mdi-help-circle-outline");
        getNgContext().save(notification);
    }
    public void update(Depense depense) throws Exception {
        getNgContext().update(depense);
        Notification notification = new Notification();
        notification.setLibele("update expense : " + depense.getIdDepense());
        notification.setType("secondary");
        notification.setIcon("mdi mdi-lead-pencil");
        getNgContext().save(notification);
    }

    public DepenseStat getDepense(int annee) throws Exception {
        return DepenseStat.getDepenseStat(annee, getNgContext());
    }

    public void delete(String idDepense) throws Exception {
        getNgContext().delete(Depense.class, Integer.parseInt(idDepense));

        Notification notification = new Notification();
        notification.setLibele("Delete expense : " + idDepense);
        notification.setType("danger");
        notification.setIcon("mdi mdi-delete-forever");
        getNgContext().save(notification);
    }
}

