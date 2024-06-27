package org.photobooth.restapi.service;

import org.entityframework.dev.Metric;
import org.entityframework.error.EntityNotFoundException;
import org.entityframework.tools.RowResult;
import org.photobooth.restapi.http.data.MaterielAddData;
import org.photobooth.restapi.model.Depense;
import org.photobooth.restapi.model.Materiel;
import org.photobooth.restapi.model.Notification;
import org.photobooth.restapi.model.stat.AllTimeUsedMateriel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterielService extends Service {
    public MaterielService() {
        super();
    }

    public List<Materiel> getAllMateriel() throws Exception {
        List<Materiel> materiels = this.getAll(Materiel.class);
        for(Materiel m : materiels) {
            m.setInUsed(getUsedCount(m.getId_materiel()));
        }
        return materiels;
    }

    private int getUsedCount(String id_materiel) throws Exception {
        RowResult result = getNgContext().execute("SELECT SUM(quantite) as count from materiel_theme where id_materiel = ?", id_materiel);
        if (result.next()) {
            Long res =  (Long) result.get("count");
            if (res != null) {
                return res.intValue();
            }
        }
        return 0;
    }

    public Optional<Materiel> getMaterielById(String id) throws Exception {
        try {
            Materiel materiel = getNgContext().findById(id, Materiel.class);
            return Optional.ofNullable(materiel);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public void add(MaterielAddData materielAddData) throws Exception {
        Materiel mt = getNgContext().findById(materielAddData.getId_materiel(), Materiel.class);
        mt.setQuantite(mt.getQuantite() + materielAddData.getNew_quantite());
        getNgContext().update(mt);

        Notification notification = new Notification();
        notification.setLibele("New material added (Toggle) : " + mt.getIntitule());
        notification.setType("info");
        notification.setIcon("mdi mdi-bookmark-plus-outline");
        getNgContext().save(notification);

        Depense depense = new Depense();
        depense.setLibele("Achat materiel");
        depense.setMontant(mt.getPrix() * materielAddData.getNew_quantite());
        depense.setDate_insertion(new Date(new java.util.Date().getTime()));
        getNgContext().save(depense);

        Notification notification2 = new Notification();
        notification2.setLibele("New expense added : " + depense.getMontant());
        notification2.setType("warning");
        notification2.setIcon("mdi mdi-help-circle-outline");
        getNgContext().save(notification2);
    }

    public String save(Materiel materiel) throws Exception {

        Metric.print(materiel);
        materiel.setImage_url("");
        materiel.setPrix_achat(0.0);
        String s =  (String) getNgContext().save(materiel);

        Notification notification = new Notification();
        notification.setLibele("New material added (Achat) : " + materiel.getIntitule());
        notification.setType("info");
        notification.setIcon("mdi mdi-bookmark-plus-outline");
        getNgContext().save(notification);

        Depense depense = new Depense();
        depense.setLibele("Achat materiel");
        depense.setMontant(materiel.getPrix() * materiel.getQuantite());
        depense.setDate_insertion(new Date(new java.util.Date().getTime()));
        getNgContext().save(depense);

        Notification notification2 = new Notification();
        notification2.setLibele("New expense added : " + depense.getMontant());
        notification2.setType("warning");
        notification2.setIcon("mdi mdi-help-circle-outline");
        getNgContext().save(notification2);

        return s;
    }

    public void update(Materiel materiel) throws Exception {
        getNgContext().update(materiel);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Materiel.class, id);

        Notification notification = new Notification();
        notification.setLibele("Delete material : " + id);
        notification.setType("danger");
        notification.setIcon("mdi mdi-delete-forever");
        getNgContext().save(notification);
    }

    public AllTimeUsedMateriel getStat() throws Exception {
        RowResult rs = getNgContext().execute("SELECT * FROM v_used_materiel");
        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        while(rs.next()) {
            labels.add((String) rs.get("intitule"));
            Long used = (Long) rs.get("sum");
            if (used == null) {
                data.add(0);
            } else {
                data.add(used.intValue());
            }
        }

        AllTimeUsedMateriel res = new AllTimeUsedMateriel();
        res.setData(data);
        res.setLabels(labels);
        return res;
    }
}
