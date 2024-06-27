package org.photobooth.restapi.service;

import org.entityframework.dev.Calculator;
import org.entityframework.dev.GenericObject;
import org.entityframework.dev.Metric;
import org.photobooth.restapi.model.Categorie_theme;
import org.photobooth.restapi.model.ai.MonthPredict;
import org.photobooth.restapi.model.stat.ThemeCategStat;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PredictionService extends Service {

    private static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "Invalid month";
        }
    }

    private int getCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonthValue() + 1;
    }

    private int getCurrentyear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();
    }

    public PredictionService() {
        super();
    }

    public static double proportion(double lastYear, double currYear) {
        if (lastYear == 0) {
            throw new IllegalArgumentException("Last year's value cannot be zero to avoid division by zero.");
        }
        return currYear / lastYear;
    }

    public MonthPredict getNextMonthPrediction() throws Exception {
        int currMonthInt =  getCurrentMonth();
        String currMonthStr = getMonthName(currMonthInt);

        MonthPredict nextStat = new MonthPredict();
        nextStat.setMonth(currMonthStr);

        int lastYearInt = getCurrentyear() - 1;

        try (StatService statService = new StatService()) {
            GenericObject lastYear = statService.getFinancialStat(lastYearInt);
            GenericObject currYear = statService.getFinancialStat(getCurrentyear());

            List<Double> beneficeLastYear = (List<Double>) lastYear.getAttribute("benefice");
            List<Double> beneficeCurrYear = (List<Double>) currYear.getAttribute("benefice");

            List<Double> benToCheckLast = Metric.getFirstNElements(beneficeLastYear, currMonthInt);
            List<Double> benToCheckCurr = Metric.getFirstNElements(beneficeCurrYear, currMonthInt);

            double totalLast = Calculator.calculateSum(benToCheckLast);
            double totalCurr = Calculator.calculateSum(benToCheckCurr);
            System.out.println("totalLast = " + totalLast);
            System.out.println("totalCurr = " + totalCurr);

            double averageLast = (totalLast);
            System.out.println("averageLast = " + averageLast);

            double proportion = proportion(averageLast, totalCurr);
            System.out.println("proportion = " + proportion);

            double lastBenCurrMonth = beneficeLastYear.get(currMonthInt);
            System.out.println("lastBenCurrMonth = " + lastBenCurrMonth);


            double averagLastMonth = (lastBenCurrMonth);
            double beneficeNextMonth = averagLastMonth * proportion;

            System.out.println("averagLastMonth = " + averagLastMonth);
            System.out.println("beneficeNextMonth = " + beneficeNextMonth);

            nextStat.setBenefice(beneficeNextMonth);
            List<ThemeCategStat> suggestion = getCategToUse(currMonthInt, getCurrentyear());
            nextStat.setSuggestion(suggestion);

            return nextStat;
        }
    }

    private List<ThemeCategStat> getCategToUse(int currMonth , int currYear) throws Exception {
        String query = "SELECT " +
                "    ct.id_categorie_theme, " +
                "    ct.intitule AS categorie_theme, " +
                "    COUNT(r.id_reservation) AS nombre_reservations " +
                "FROM " +
                "    categorie_theme ct " +
                "LEFT JOIN (" +
                "    SELECT " +
                "        r.id_reservation, " +
                "        t.id_categorie_theme " +
                "    FROM " +
                "        reservation r " +
                "    JOIN " +
                "        theme t ON r.id_theme = t.id_theme " +
                "    WHERE " +
                "        EXTRACT(MONTH FROM r.heure_debut) = ? " +
                "        AND EXTRACT(YEAR FROM r.heure_debut) = ? " +
                ") r ON ct.id_categorie_theme = r.id_categorie_theme " +
                "GROUP BY " +
                "    ct.id_categorie_theme, " +
                "    ct.intitule " +
                "ORDER BY " +
                "    nombre_reservations DESC";


        List<ThemeCategStat> categStatsLast = getNgContext().executeToList(ThemeCategStat.class, query, currMonth + 1, currYear - 1);

        List<ThemeCategStat> suggestion = ThemeCategStat.suggestRanking(new ArrayList<>(), categStatsLast);

        if (suggestion.size() > 3) {
            return Metric.getFirstNElements(suggestion, 3);
        }

        return null;
    }
}
