package org.photobooth.restapi.http.data;

import java.sql.Timestamp;

public class ReservationInterval {
    private Timestamp debut;
    private Timestamp fin;

    public ReservationInterval() {
    }

    public Timestamp getDebut() {
        return debut;
    }

    public void setDebut(Timestamp debut) {
        this.debut = debut;
    }

    public Timestamp getFin() {
        return fin;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }
}
