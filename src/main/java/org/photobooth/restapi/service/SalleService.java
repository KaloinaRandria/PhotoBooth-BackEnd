package org.photobooth.restapi.service;

import org.photobooth.restapi.model.Membre;
import org.photobooth.restapi.model.Notification;
import org.photobooth.restapi.model.Role;
import org.photobooth.restapi.model.Salle;
import org.photobooth.restapi.model.img.ImageSalle;

import java.util.List;

public class SalleService extends Service{
    public SalleService() {
        super();
    }

    public List<Salle> getAllSalle() throws Exception {
        List<Salle> salles = this.getAll(Salle.class);
        for (Salle salle : salles) {
            salle.setImageSalle(getImageSalle(salle.getId_salle()));
        }
        return salles;
    }

    private List<ImageSalle> getImageSalle(String id_salle) throws Exception {
        return getNgContext().findWhere(ImageSalle.class, "id_salle = '" + id_salle + "'");
    }

    public String insertSalle(Salle salle) throws Exception {
        String s =  (String) getNgContext().save(salle);
        Notification notification = new Notification();
        notification.setLibele("New room added : " + s);
        notification.setType("info");
        notification.setIcon("mdi mdi-bookmark-plus-outline");
        getNgContext().save(notification);

        return s;
    }

    public void update(Salle salle) throws Exception {
        getNgContext().update(salle);

        Notification notification = new Notification();
        notification.setLibele("Update room : " + salle.getId_salle());
        notification.setType("secondary");
        notification.setIcon("mdi mdi-lead-pencil");
        getNgContext().save(notification);
    }

    public void delete(String id) throws Exception {
        getNgContext().delete(Salle.class, id);

        Notification notification = new Notification();
        notification.setLibele("Delete room : " + id);
        notification.setType("danger");
        notification.setIcon("mdi mdi-delete-forever");
        getNgContext().save(notification);
    }

}
