package org.photobooth.restapi.model.stat;

import java.sql.Date;

public class ReservationStat {
    public Date startDate;
    public Date endDate;
    public int nbReservation;
    public int confirmedReservation;
    public int canceledReservation;
    public int waitingReservation;

    public ReservationStat() {}

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNbReservation() {
        return nbReservation;
    }

    public void setNbReservation(int nbReservation) {
        this.nbReservation = nbReservation;
    }

    public int getConfirmedReservation() {
        return confirmedReservation;
    }

    public void setConfirmedReservation(int confirmedReservation) {
        this.confirmedReservation = confirmedReservation;
    }

    public int getCanceledReservation() {
        return canceledReservation;
    }

    public void setCanceledReservation(int canceledReservation) {
        this.canceledReservation = canceledReservation;
    }

    public int getWaitingReservation() {
        return waitingReservation;
    }

    public void setWaitingReservation(int waitingReservation) {
        this.waitingReservation = waitingReservation;
    }
}
