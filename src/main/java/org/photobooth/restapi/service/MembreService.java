package org.photobooth.restapi.service;

import org.entityframework.client.GenericEntity;
import org.entityframework.dev.Metric;
import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.model.Membre;
import org.photobooth.restapi.model.Salaire;

import java.sql.Timestamp;
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
        Salaire salaire = getNgContext().findExtreme(Salaire.class, "date_insertion", GenericEntity.MAX, "id_membre = '" + membre.getId_membre() + "'", "id_membre = '" + membre.getId_membre() + "'");
        Metric.print(salaire);
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
        try {
            getNgContext().setAutoCommit(false);
            String saved = (String) getNgContext().save(membre);
            Salaire sal = new Salaire();
            sal.setId_membre(saved);
            sal.setMontant(membre.getSalaire().getMontant());
            sal.setDate_insertion(new Timestamp(System.currentTimeMillis()));
            getNgContext().save(sal);

            getNgContext().commit();
            getNgContext().setAutoCommit(true);
            return saved;
        } catch (Exception e) {
            getNgContext().rollBack();
            getNgContext().commit();
            getNgContext().setAutoCommit(true);
            throw e;
        }
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
        try {
            getNgContext().setAutoCommit(false);

            getNgContext().update(updatedMember);
            Salaire sal = updatedMember.getSalaire();
            sal.setDate_insertion(new Timestamp(System.currentTimeMillis()));
            getNgContext().save(sal);

            getNgContext().commit();
            getNgContext().setAutoCommit(true);
        } catch(Exception e) {
            getNgContext().rollBack();
            getNgContext().commit();
            getNgContext().setAutoCommit(true);
            throw e;
        }
    }
}
