package org.photobooth.restapi.service;

import org.entityframework.dev.Metric;
import org.entityframework.error.EntityNotFoundException;
import org.entityframework.tools.RowResult;
import org.photobooth.restapi.http.data.MaterielAddData;
import org.photobooth.restapi.model.Materiel;
import org.photobooth.restapi.model.stat.AllTimeUsedMateriel;

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
    }

    public String save(Materiel materiel) throws Exception {

        Metric.print(materiel);
        materiel.setImage_url("");
        return (String) getNgContext().save(materiel);
    }

    public void update(Materiel materiel) throws Exception {
        getNgContext().update(materiel);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Materiel.class, id);
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
