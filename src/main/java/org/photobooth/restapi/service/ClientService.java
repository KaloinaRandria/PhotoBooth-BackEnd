package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.Materiel;
import org.photobooth.restapi.model.Notification;

import java.util.List;
import java.util.Optional;

public class ClientService extends Service {
    public  ClientService() {super();}

    public List<Client> getAllClient() throws Exception {
        return  this.getAll(Client.class);
    }

    public Optional<Client> getClientById(String id) throws  Exception{
        try {
            Client client = getNgContext().findById(id,Client.class);
            return Optional.ofNullable(client);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public String save(Client client) throws Exception {
        String s = (String) getNgContext().save(client);

        Notification notification = new Notification();
        notification.setLibele("New client added : " + client.getNom() + "(" + s + ")");
        notification.setType("info");
        notification.setIcon("mdi mdi-bookmark-plus-outline");
        getNgContext().save(notification);

        return s;
    }

    public void update(Client client) throws Exception {
        getNgContext().update(client);

        Notification notification = new Notification();
        notification.setLibele("Update client : " + client.getId_client());
        notification.setType("secondary");
        notification.setIcon("mdi mdi-lead-pencil");
        getNgContext().save(notification);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Client.class, id);

        Notification notification = new Notification();
        notification.setLibele("Delete client : " + id);
        notification.setType("danger");
        notification.setIcon("mdi mdi-delete-forever");
        getNgContext().save(notification);
    }
}
