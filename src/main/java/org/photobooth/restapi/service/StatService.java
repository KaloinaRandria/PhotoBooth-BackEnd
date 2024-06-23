package org.photobooth.restapi.service;

import org.entityframework.dev.Calculator;
import org.entityframework.dev.GenericObject;
import org.entityframework.dev.Metric;
import org.entityframework.tools.RowResult;
import org.photobooth.restapi.model.Reservation;
import org.photobooth.restapi.model.stat.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
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

    public ServiceStat getServiceStat(int choice) throws Exception {
        String query = "";
        if (choice == 1) {
            query = "SELECT * from v_service_stat_today";
        } else if (choice == 2) {
            query = "SELECT * from v_service_stat_last_30_days";
        } else if (choice == 3) {
            query = "SELECT * from v_service_stat_this_year";
        }

        ServiceStat serviceStat = new ServiceStat();
        List<String> categories = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        List<String> colors = new ArrayList<>();

        RowResult rs = getNgContext().execute(query);
        while (rs.next()) {
            colors.add((String) rs.get(1));
            categories.add((String) rs.get(2));
            data.add(rs.get(3));
        }

        serviceStat.setCategories(categories);
        serviceStat.setData(data);
        serviceStat.setColors(colors);

        return serviceStat;
    }

    public ReservationStat getResaStat(ReservationStat reservationStatS) throws Exception {
        Date startDate = reservationStatS.getStartDate();
        Date endDate = reservationStatS.getEndDate();
        List<Reservation> reservations = getNgContext().findWhereArgs(Reservation.class, "date_reservation between ? and ?",  new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));

        int cancel = 0;
        int confirm = 0;
        int waiting = 0;

        for (Reservation reservation : reservations) {
            if (reservation.isValid() && reservation.isConfirmed()) {
                confirm++;
            } else if (!reservation.isValid()) {
                cancel++;
            } else if (reservation.isValid() && !reservation.isConfirmed()) {
                waiting++;
            }
        }
        int nbResa = reservations.size();
        ReservationStat reservationStat = new ReservationStat();
        reservationStat.setStartDate(startDate);
        reservationStat.setEndDate(endDate);
        reservationStat.setConfirmedReservation(confirm);
        reservationStat.setCanceledReservation(cancel);
        reservationStat.setNbReservation(nbResa);
        reservationStat.setWaitingReservation(waiting);
        return reservationStat;
    }

    public GenericObject getResaYearStat(int year) throws Exception {
        String query = "WITH months AS (SELECT generate_series(1, 12) AS mois)," +
                " reservations_status AS (SELECT EXTRACT(MONTH FROM r.date_reservation) AS mois, COUNT(*) AS total_reservations, SUM(CASE WHEN r.isValid = false THEN 1 ELSE 0 END) AS reservations_annulees, SUM(CASE WHEN r.isValid = true AND r.isConfirmed = true THEN 1 ELSE 0 END) AS reservations_confirmees, SUM(CASE WHEN r.isValid = true AND r.isConfirmed = false THEN 1 ELSE 0 END) AS reservations_en_attente FROM reservation r WHERE EXTRACT(YEAR FROM r.date_reservation) = ? GROUP BY EXTRACT(MONTH FROM r.date_reservation))" +
                " SELECT m.mois AS mois, COALESCE(rs.total_reservations, 0) AS total_reservations, COALESCE(rs.reservations_annulees, 0) AS reservations_annulees, COALESCE(rs.reservations_confirmees, 0) AS reservations_confirmees, COALESCE(rs.reservations_en_attente, 0) AS reservations_en_attente FROM months m LEFT JOIN reservations_status rs ON m.mois = rs.mois ORDER BY m.mois";
        RowResult rs = getNgContext().execute(query, year);
        List<Long> resa = new ArrayList<>();
        List<Long> confirm = new ArrayList<>();
        List<Long> cancel = new ArrayList<>();
        List<Long> attente = new ArrayList<>();

        while (rs.next()) {
            resa.add((Long)rs.get(2));
            cancel.add((Long)rs.get(3));
            confirm.add((Long)rs.get(4));
            attente.add((Long)rs.get(5));
        }

        GenericObject genericObject = new GenericObject();
        genericObject.addAttribute("total_reservation", resa);
        genericObject.addAttribute("confirmed_reservation", confirm);
        genericObject.addAttribute("canceled_reservation", cancel);
        genericObject.addAttribute("processing_reservation", attente);

        genericObject.addAttribute("nb_total_reservation", Calculator.calculateSum(resa));
        genericObject.addAttribute("nb_confirmed_reservation", Calculator.calculateSum(confirm));
        genericObject.addAttribute("nb_canceled_reservation", Calculator.calculateSum(cancel));
        genericObject.addAttribute("nb_processing_reservation", Calculator.calculateSum(attente));

        int maxIndex = -1;
        long maxValue = Long.MIN_VALUE;
        for (int i = 0; i < resa.size(); i++) {
            long value = resa.get(i);
            if (value > maxValue) {
                maxValue = value;
                maxIndex = i;
            }
        }

        // Déterminer le mois correspondant (les indices sont de 0 à 11)
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String maxMonth = months[maxIndex];

        // Calculer la moyenne
        long sum = 0;
        for (Long obj : resa) {
            sum += obj;
        }
        double average = sum / (double) resa.size();

        genericObject.addAttribute("best_month", maxMonth);
        genericObject.addAttribute("average", average);
        genericObject.addAttribute("max_value", maxValue);

        return genericObject;
    }
}
