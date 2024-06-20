package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.entityframework.tools.RowResult;
import org.photobooth.restapi.http.data.ReservationInterval;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.Reservation;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class ReservationService extends Service {

    public ReservationService() {
        super();
    }
    public List<Reservation> getAllReservation() throws Exception {
        return  this.getAll(Reservation.class);
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
        return (String) getNgContext().save(reservation);
    }

    public void update(Reservation reservation) throws Exception {
        getNgContext().update(reservation);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Client.class, id);
    }

    public boolean isAvailable(ReservationInterval interval) throws Exception {
        return isAvailable(interval.getDebut(), interval.getFin());
    }

    private boolean isAvailable(Timestamp start, Timestamp end) throws Exception {
        String query = "SELECT COUNT(*) FROM reservation WHERE " +
                "(heure_debut BETWEEN ? AND ?) " +
                "OR (heure_fin BETWEEN ? AND ?) " +
                "OR (heure_debut <= ? AND heure_fin >= ?)";

        RowResult rs = getNgContext().execute(query, start, end, start, end, start, end);
        return rs.isEmpty();
    }
}
