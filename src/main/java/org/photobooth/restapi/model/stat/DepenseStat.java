package org.photobooth.restapi.model.stat;

import org.entityframework.client.GenericEntity;
import org.entityframework.tools.RowResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DepenseStat {
    private int annee;
    private List<Object> data;

    public List<Double> doubleData() {
        List<Double> doubleList = new ArrayList<>();
        for (Object o : data) {
            doubleList.add(((BigDecimal) o).doubleValue());
        }
        return doubleList;
    }

    public DepenseStat() {}

    public static DepenseStat getDepenseStat(int annee, GenericEntity ng) throws Exception {
        String query = "SELECT all_months.mois, COALESCE(SUM(depense.montant), 0) AS total_depense FROM generate_series(1, 12) AS all_months(mois) LEFT JOIN depense ON EXTRACT(MONTH FROM depense.date_insertion) = all_months.mois AND EXTRACT(YEAR FROM depense.date_insertion) = ? GROUP BY all_months.mois ORDER BY all_months.mois";
        RowResult result = ng.execute(query, annee);
        List<Object> data = new ArrayList<>();
        while (result.next()) {
            data.add(result.get("total_depense"));
        }
        DepenseStat st = new DepenseStat();
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
