package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.Reservation;

import java.util.List;
import java.util.Optional;

public class ReservationService extends Service {
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


}
