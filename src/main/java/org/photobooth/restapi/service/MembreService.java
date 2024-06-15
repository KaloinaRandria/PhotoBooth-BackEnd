package org.photobooth.restapi.service;

import org.entityframework.client.GenericEntity;
import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.Membre;
import org.photobooth.restapi.model.Salaire;

import java.util.List;
import java.util.Optional;

public class MembreService extends Service{
    public MembreService() {
        super();
    }

    public Membre connect(Membre membre) throws Exception {
        List<Membre> membres = getNgContext().findWhere(Membre.class , membre);
        if (membres.isEmpty()) {
            throw new Exception("Username or password incorrect!");
        }
        Membre membreConnected = membres.get(0);
        getAndSetMembreSalaire(membreConnected);
        return membreConnected;
    }

    private void getAndSetMembreSalaire(Membre membre) throws Exception {
        Salaire salaire = getNgContext().findExtreme(Salaire.class, "date_insertion", GenericEntity.MAX, "id_membre = '" + membre.getId_membre() + "'");
        membre.setSalaire(salaire);
    }

    public List<Membre> getAllMembre() throws Exception {
        List<Membre> membres = getNgContext().findAll(Membre.class);
        for (Membre membre : membres) {
            getAndSetMembreSalaire(membre);
        }
        return membres;
    }

    public String insertMembre(Membre membre) throws Exception {
        return (String) getNgContext().save(membre);
    }

    public Optional<Membre> getMembreById(String id) throws Exception {
        try {
            Membre membre = getNgContext().findById(id, Membre.class);
            return Optional.ofNullable(membre);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public void updateUser(Membre updatedMember) throws Exception {
        getNgContext().update(updatedMember);
    }
}
