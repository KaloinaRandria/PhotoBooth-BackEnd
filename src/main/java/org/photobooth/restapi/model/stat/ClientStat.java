package org.photobooth.restapi.model.stat;

import org.photobooth.restapi.model.Client;

public class ClientStat {
    private Client client;
    private int nb_reservations;
    private double total_prix;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getNb_reservations() {
        return nb_reservations;
    }

    public void setNb_reservations(int nb_reservations) {
        this.nb_reservations = nb_reservations;
    }

    public double getTotal_prix() {
        return total_prix;
    }

    public void setTotal_prix(double total_prix) {
        this.total_prix = total_prix;
    }
}
