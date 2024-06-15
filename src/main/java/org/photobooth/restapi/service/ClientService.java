package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.Materiel;

import java.util.List;
import java.util.Optional;

public class ClientService extends Service {
    public  ClientService() {super();}

    public List<Client> getAllClient() throws Exception {
        return this.getAll(Client.class);
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
        return (String) getNgContext().save(client);
    }

    public void update(Client client) throws Exception {
        getNgContext().update(client);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Client.class, id);
    }
}
