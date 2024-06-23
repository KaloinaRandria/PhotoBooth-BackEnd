package org.photobooth.restapi.service;

import org.entityframework.dev.Calculator;
import org.entityframework.dev.GenericObject;
import org.entityframework.dev.Metric;
import org.photobooth.restapi.model.stat.ChiffreStat;
import org.photobooth.restapi.model.stat.DepenseStat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        double currben = Calculator.calculateSum(beneficeStat);
        ge.addAttribute("total_benefice", currben);
        double currDep = Calculator.calculateSum(depenseStat.doubleData());
        ge.addAttribute("total_depense", currDep);

        int lastYear = annee - 1;
        ChiffreStat chiffreStatLast = ChiffreStat.getChiffreStat(lastYear, getNgContext());
        DepenseStat depenseStatLast = DepenseStat.getDepenseStat(lastYear, getNgContext());

        List<Double> beneficeStatLast = new ArrayList<>();

        int iLast = 0;
        for (Object object : chiffreStatLast.getData()) {
            double ch = ((BigDecimal) object).doubleValue();
            double de = ((BigDecimal) depenseStatLast.getData().get(iLast)).doubleValue();
            double be = ch - de;
            beneficeStatLast.add(be);
            iLast++;
        }

        LocalDate currentDate = LocalDate.now();
        int monthValue = currentDate.getMonthValue() + 1;
        int year = currentDate.getYear();

        System.out.println("year = " + year + " annee = " + annee);
        System.out.println("month  = " + monthValue);

        if (annee == year) {
            List<Double> lastDepMonth = Metric.getFirstNElements(depenseStatLast.doubleData(), monthValue);
            double lastDepense =  Calculator.calculateSum(lastDepMonth);
            double lastBenefice = Calculator.calculateSum(Metric.getFirstNElements(beneficeStatLast, monthValue));

            System.out.println("Ben : " + lastBenefice + " | " + currben);
            System.out.println("dep : " + lastDepense + " | " + currDep);

            double diffDep = calculatePercentageChange(lastDepense, currDep);
            ge.addAttribute("diff_depense", diffDep);

            double diffBen = calculatePercentageChange(lastBenefice, currben);
            ge.addAttribute("diff_benefice", diffBen);

        } else {
            double lastDepense =  Calculator.calculateSum(depenseStatLast.doubleData());
            double lastBenefice =  Calculator.calculateSum(beneficeStatLast);

            double diffDep = calculatePercentageChange(lastDepense, currDep);
            if (lastDepense == 0) {
                ge.addAttribute("diff_depense", " -- ");
            } else {
                ge.addAttribute("diff_depense", diffDep);
            }


            double diffBen = calculatePercentageChange(lastBenefice, currben);
            if (lastBenefice == 0) {
                ge.addAttribute("diff_benefice", " -- ");
            } else {
                ge.addAttribute("diff_benefice", diffBen);
            }
        }
        return ge;
    }

    private static double calculatePercentageChange(double beneficeLastYear, double beneficeCurrentYear) {
        double difference = beneficeCurrentYear - beneficeLastYear;
        double percentageChange = (difference / Math.abs(beneficeLastYear)) * 100;
        return percentageChange;
    }
}
