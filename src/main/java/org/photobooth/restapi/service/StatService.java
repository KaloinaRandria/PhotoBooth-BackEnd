package org.photobooth.restapi.service;

import org.entityframework.client.GenericEntity;
import org.entityframework.dev.Calculator;
import org.entityframework.dev.GenericObject;
import org.entityframework.dev.Metric;
import org.entityframework.tools.RowResult;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.Materiel;
import org.photobooth.restapi.model.Reservation;
import org.photobooth.restapi.model.ServComp;
import org.photobooth.restapi.model.stat.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

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

    public GenericObject getAllTimeClientStat(GenericEntity ng, String id_client) throws Exception {
        String query = "SELECT * FROM v_client_stat where id_client = ?";
        RowResult rs = getNgContext().execute(query, id_client);

        GenericObject ge = new GenericObject();
        if (rs.next()) {
            ge.addAttribute("client", ng.findById((String) rs.get(1), Client.class));
            ge.addAttribute("nb_reservation", rs.get(3));
            ge.addAttribute("total_prix", rs.get(4));
        }
        return ge;
    }

    public List<ClientStat> getTopClient(int number) throws Exception {
        String query = "SELECT * FROM v_client_stat order by nb_reservations desc limit ?";
        RowResult rs = getNgContext().execute(query, number);

        List<ClientStat> clientStats = new ArrayList<>();
        while (rs.next()) {
            ClientStat clientStat = new ClientStat();
            Client client = getNgContext().findById((String) rs.get(1), Client.class);
            clientStat.setClient(client);
            BigDecimal pr = (BigDecimal) rs.get(4);
            clientStat.setTotal_prix(pr.doubleValue());
            Long nb = (Long) rs.get(3);
            clientStat.setNb_reservations(nb.intValue());
            clientStats.add(clientStat);
        }
        return clientStats;
    }

    private static String conv(double value) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        return bigDecimal.toPlainString();
    }

    public GenericObject allProfit() throws Exception {
        RowResult rs = getNgContext().execute("SELECT * from v_profit");
        List<Object[]> data = new ArrayList<>();

        while (rs.next()) {
            Double dtBigDecimal = (Double) rs.get(1);
            long timestamp = dtBigDecimal.longValue();
            double value = ((BigDecimal) rs.get(2)).doubleValue();

            Object[] entry = { timestamp, value };
            data.add(entry);
        }

        GenericObject genericObject = new GenericObject();
        genericObject.addAttribute("data", data);

        return genericObject;
    }

    public MostServiceStat getMostService(MostServiceStat serviceS) throws Exception {
        String query = "SELECT id_service, COUNT(*) AS usage_count" +
                "    FROM reservation" +
                "    WHERE date_reservation BETWEEN ? AND ?" +
                "    GROUP BY id_service" +
                "    ORDER BY usage_count DESC LIMIT 1";
        RowResult rs = getNgContext().execute(query, serviceS.getStart(), serviceS.getEnd());

        if (rs.isEmpty()) {
            return serviceS;
        }

        if (rs.next()) {
            ServComp servComp = getNgContext().findById((String) rs.get(1), ServComp.class);
            int count = ((Long) rs.get(2)).intValue();
            serviceS.setService(servComp);
            serviceS.setUse_count(count);
        }
        return serviceS;
    }

    private String formatBigDecimal(BigDecimal value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,###.##", symbols);
        return df.format(value);
    }

    public GenericObject getFinancial(Date start , Date end) throws Exception {
        String query = "WITH chiffre_affaire AS (" +
                "        SELECT SUM(montant_entrant) AS total_chiffre_affaire" +
                "        FROM historique" +
                "        WHERE date_action BETWEEN ? AND ?" +
                "    )," +
                "         total_depenses AS (" +
                "             SELECT SUM(montant) AS total_depense" +
                "             FROM depense" +
                "             WHERE date_insertion BETWEEN ? AND ?" +
                "         )" +
                "    SELECT" +
                "        COALESCE(total_chiffre_affaire, 0) AS chiffre_affaire," +
                "        COALESCE(total_depense, 0) AS depense," +
                "        COALESCE(total_chiffre_affaire, 0) - COALESCE(total_depense, 0) AS benefice" +
                "    FROM" +
                "        chiffre_affaire, total_depenses";

        RowResult rs = getNgContext().execute(query, start, end , start, end);

        if(!rs.isEmpty()) {
            if(rs.next()) {
                BigDecimal chiffre = (BigDecimal) rs.get(1);
                BigDecimal depense = (BigDecimal) rs.get(2);
                BigDecimal benefice = (BigDecimal) rs.get(3);

                String formattedChiffre = formatBigDecimal(chiffre);
                String formattedDepense = formatBigDecimal(depense);
                String formattedBenefice = formatBigDecimal(benefice);

                GenericObject genericObject = new GenericObject();
                genericObject.addAttribute("chiffre", formattedChiffre);
                genericObject.addAttribute("depense", formattedDepense);
                genericObject.addAttribute("benefice", formattedBenefice);

                query = "SELECT COALESCE(SUM(nb_personne), 0) AS total_personnes " +
                        "FROM reservation " +
                        "WHERE date_reservee BETWEEN ? AND ?";

                RowResult rs2 = getNgContext().execute(query, start, end);
                if(rs2.next()) {
                    genericObject.addAttribute("visit", rs2.get(1));
                }

                return genericObject;
            }
        }
        throw new Exception("wait , nothing found!");
    }

    public MatStat getMatStat() throws Exception {
        MatStat matStat = new MatStat();

        String query = "SELECT SUM(worth) from v_theme_worth";
        RowResult rs = getNgContext().execute(query);
        if (rs.next()) {
            BigDecimal to = (BigDecimal) rs.get(1);
            matStat.setTotal(to.doubleValue());
        }
        return matStat;
    }
}
