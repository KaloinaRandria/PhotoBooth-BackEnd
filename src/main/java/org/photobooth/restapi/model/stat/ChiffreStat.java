package org.photobooth.restapi.model.stat;

import org.entityframework.client.GenericEntity;
import org.entityframework.tools.RowResult;

import java.util.ArrayList;
import java.util.List;

public class ChiffreStat {
    private int annee;
    private List<Object> data;

    public ChiffreStat() {}

    public static ChiffreStat getChiffreStat(int annee , GenericEntity ng) throws Exception {
        String query = "SELECT all_months.mois, COALESCE(SUM(historique.montant_entrant), 0) AS total_chiffre FROM generate_series(1, 12) AS all_months(mois) LEFT JOIN historique ON EXTRACT(MONTH FROM historique.date_action) = all_months.mois AND EXTRACT(YEAR FROM historique.date_action) = ? GROUP BY all_months.mois ORDER BY all_months.mois";
        RowResult result = ng.execute(query, annee);
        List<Object> data = new ArrayList<>();
        while (result.next()) {
            data.add(result.get("total_chiffre"));
        }
        ChiffreStat st = new ChiffreStat();
        st.setAnnee(annee);
        st.setData(data);
        return st;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
