package org.photobooth.restapi.service;

import org.entityframework.dev.GenericObject;
import org.photobooth.restapi.model.stat.ChiffreStat;
import org.photobooth.restapi.model.stat.DepenseStat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StatService extends Service {
    public StatService() {super();}

    public GenericObject getFinancialStat(int annee) throws Exception {
        ChiffreStat chiffreStat = ChiffreStat.getChiffreStat(annee, getNgContext());
        DepenseStat depenseStat = DepenseStat.getDepenseStat(annee, getNgContext());

        List<Double> beneficeStat = new ArrayList<>();

        int i = 0;
        for (Object object : chiffreStat.getData()) {
            double ch = ((BigDecimal) object).doubleValue();
            double de = ((BigDecimal) depenseStat.getData().get(i)).doubleValue();
            double be = ch - de;
            beneficeStat.add(be);
            i++;
        }

        GenericObject ge = new GenericObject();
        ge.addAttribute("annee", annee);
        ge.addAttribute("benefice", beneficeStat);
        ge.addAttribute("chiffre", chiffreStat.getData());
        ge.addAttribute("depense", depenseStat.getData());
        return ge;
    }
}
