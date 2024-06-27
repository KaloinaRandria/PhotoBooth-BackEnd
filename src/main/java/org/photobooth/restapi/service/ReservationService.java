package org.photobooth.restapi.service;

import org.entityframework.dev.GenericObject;
import org.entityframework.dev.Metric;
import org.entityframework.error.EntityNotFoundException;
import org.entityframework.tools.RowResult;
import org.photobooth.restapi.http.data.ReservationInterval;
import org.photobooth.restapi.http.data.Shedule;
import org.photobooth.restapi.model.*;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ReservationService extends Service {

    public ReservationService() {
        super();
    }
    public List<Reservation> getAllReservation() throws Exception {
        return getNgContext().findWhereArgs(Reservation.class, "isValid = ? and isConfirmed = false and date_reservee >= current_date order by heure_debut ", true);
    }

    public List<Reservation> getAllReservationDone() throws Exception {
        return getNgContext().findWhereArgs(Reservation.class, "isValid = false or isConfirmed = true order by heure_debut desc");
    }
    public Optional<Reservation> getReservationById(String id) throws  Exception{
        try {
            Reservation reservation = getNgContext().findById(id,Reservation.class);
            return Optional.ofNullable(reservation);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public String save(Reservation reservation) throws Exception {
        reservation.setDate_reservee(new Date(reservation.getHeure_debut().getTime()));
        reservation.setDate_reservation(new Timestamp(System.currentTimeMillis()));
        reservation.setValid(true);
        String val = (String) getNgContext().save(reservation);
        Historique historique = new Historique();
        historique.setDate_action(new Date(new java.util.Date().getTime()));
        historique.setDate_debut(reservation.getHeure_debut());
        historique.setDate_fin(reservation.getHeure_fin());
        historique.setTheme(reservation.getTheme());
        historique.setMontant_entrant(reservation.getPrix() / 2);
        getNgContext().save(historique);

        Notification notification2 = new Notification();
        notification2.setLibele("New reservation added : " + val);
        notification2.setType("info");
        notification2.setIcon("mdi mdi-bookmark-plus-outline");
        getNgContext().save(notification2);

        Notification notification = new Notification();
        notification.setLibele("New profit added : " + reservation.getPrix() / 2);
        notification.setType("success");
        notification.setIcon("mdi mdi-coin");
        getNgContext().save(notification);

        return val;
    }

    public void update(Reservation reservation) throws Exception {
        getNgContext().update(reservation);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Client.class, id);
    }

    private double calculatePrice(Theme theme, ServComp service, int nbPersonne) throws Exception {
        System.out.println("s = " + service.getId_comp_service() + " nb = " + nbPersonne);
        String query = "SELECT tcs.prix FROM tarif_comp_service tcs JOIN value_ranges vr ON tcs.id_value_ranges = vr.id WHERE tcs.id_comp_service = ? AND ((vr.min_value IS NULL OR ? >= vr.min_value) AND (vr.max_value IS NULL OR ? <= vr.max_value))";
        RowResult rs = getNgContext().execute(query, service.getId_comp_service(), nbPersonne, nbPersonne);
        if (rs.next()) {
            BigDecimal data = (BigDecimal) rs.get("prix");
            return data.doubleValue();
        }
        throw new Exception("Unresolved calculator");
    }


    public Object isAvailable(ReservationInterval interval) throws Exception {
        GenericObject data = new GenericObject();

        boolean flag = isAvailable(interval.getDebut(), interval.getFin(), interval.getId_salle());
        data.addAttribute("flag", flag);
        data.addAttribute("bonus", false);

        if (!flag) {
            return data;
        }

        ServComp service = getNgContext().findById(interval.getId_service(), ServComp.class);
        data.addAttribute("service", service);

        Date reservationDate = new Date(interval.getDebut().getTime());
        List<Theme> themes = getNgContext().executeToList(Theme.class, "SELECT * FROM theme where date_debut <= ? and date_fin >= ? and id_salle = ?", reservationDate, reservationDate, interval.getId_salle());
        if (themes.isEmpty()) {
            data.addAttribute("flag", false);
            data.addAttribute("message", "theme is not setted on this date");
        }else {
            data.addAttribute("theme", themes.get(0));
            double price = calculatePrice(themes.get(0), service, interval.getNb_personne());


            try (StatService statService = new StatService()){
                GenericObject genericObject = statService.getAllTimeClientStat(getNgContext(), interval.getId_client());
                long nb_resa = (long) genericObject.getAttribute("nb_reservation");
                if (nb_resa % 5 == 4) {
                    data.addAttribute("bonus", true);
                    price = price - (price * 0.1);
                }
            }
            data.addAttribute("prix", price);
        }

        return data;
    }

    private boolean isAvailable(Timestamp start, Timestamp end , String id_salle) throws Exception {
        String query = "SELECT * FROM reservation WHERE " +
                "((heure_debut BETWEEN ? AND ?) " +
                "OR (heure_fin BETWEEN ? AND ?) " +
                "OR (heure_debut <= ? AND heure_fin >= ?)) AND id_salle = ?";

        RowResult rs = getNgContext().execute(query, start, end, start, end, start, end, id_salle);
        return rs.isEmpty();
    }

    private static String toHexString(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private static String fadeColor(String colorHex, double opacity) {
        Color color = Color.decode(colorHex);
        int alpha = (int) Math.round(opacity * 255); // Convertir l'opacité de 0.0 à 1.0 en valeur alpha (0 à 255)
        Color fadedColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        return toHexString(fadedColor);
    }

    public List<Shedule> getAppShedule() throws Exception {
        List<Reservation> reserv = getNgContext().findWhen(Reservation.class, "ORDER BY heure_debut");
        List<Shedule> sheduleList = new ArrayList<>();
        for(Reservation reservation : reserv) {
            Shedule shedule = new Shedule();
            shedule.setStart(reservation.getHeure_debut());
            shedule.setEnd(reservation.getHeure_fin());
            String description = reservation.getService().getIntitule() + " : " + reservation.getClient().getNom();
            shedule.setTitle("Room - " + String.valueOf(reservation.getSalle().getNumero()) + "\n" + description);
            shedule.setClassName(reservation.getService().getIntitule());
            shedule.setBackgroundColor(fadeColor(reservation.getService().getColor(), 0.3));
            shedule.setColor(fadeColor(reservation.getService().getColor(), 0.3));
            shedule.setDescription(description);
            sheduleList.add(shedule);
        }
        return sheduleList;
    }

    public boolean confirm(String idReservation) throws Exception {
        Reservation reservation = getNgContext().findById(idReservation, Reservation.class);
        reservation.setConfirmed(true);
        Metric.print(reservation);
        getNgContext().update(reservation);
        Historique historique = new Historique();
        historique.setDate_action(new Date(reservation.getHeure_debut().getTime()));
        historique.setDate_debut(reservation.getHeure_debut());
        historique.setDate_fin(reservation.getHeure_fin());
        historique.setTheme(reservation.getTheme());
        historique.setMontant_entrant(reservation.getPrix() / 2);
        getNgContext().save(historique);

        Notification notification = new Notification();
        notification.setLibele("Confirmed reservation : " + reservation.getId_reservation());
        notification.setType("primary");
        notification.setIcon("mdi mdi-marker-check");
        getNgContext().save(notification);

        Notification notification2 = new Notification();
        notification2.setLibele("New profit added : " + reservation.getPrix() / 2);
        notification2.setType("success");
        notification2.setIcon("mdi mdi-coin");
        getNgContext().save(notification2);
        return true;
    }

    private static boolean isWithinDays(Date date1, Date date2, int days) {
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diffInDays < days;
    }

    public GenericObject cancel(String idReservation) throws Exception {
        GenericObject data = new GenericObject();

        Reservation reservation = getNgContext().findById(idReservation, Reservation.class);
        Date cancelDate = new Date(new java.util.Date().getTime());
        Date beginDate = new Date(reservation.getHeure_debut().getTime());
        if (isWithinDays(cancelDate, beginDate, 3)) {
            data.addAttribute("flag" , false);
            data.addAttribute("message", "cannot cancel because ... (ef 3 jour sisa)");
            return data;
        }

        Depense depense = new Depense();
        depense.setDate_insertion(new Date(new java.util.Date().getTime()));
        depense.setLibele("Annulation de reservation");

        double ten = (reservation.getPrix() / 2) * 0.1;
        depense.setMontant(ten);
        getNgContext().save(depense);

        reservation.setValid(false);
        getNgContext().update(reservation);

        Notification notification = new Notification();
        notification.setLibele("Cancel reservation : " + idReservation);
        notification.setType("dark");
        notification.setIcon("mdi mdi-close-network");
        getNgContext().save(notification);

        Notification notification2 = new Notification();
        notification2.setLibele("New expense added : " + depense.getMontant());
        notification2.setType("warning");
        notification2.setIcon("mdi mdi-help-circle-outline");
        getNgContext().save(notification2);

        data.addAttribute("flag", true);
        data.addAttribute("message", "");
        return data;
    }
}
