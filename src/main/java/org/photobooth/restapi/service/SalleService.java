package org.photobooth.restapi.service;

import org.entityframework.tools.RowResult;
import org.photobooth.restapi.model.*;
import org.photobooth.restapi.model.img.ImageSalle;
import org.photobooth.restapi.model.img.ImageTheme;

import java.util.ArrayList;
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

    public List<CurrentState> getCurrentState() throws Exception {
        RowResult rs = getNgContext().execute("SELECT * FROM v_curr_state");
        List<CurrentState> currentStates = new ArrayList<>();

        while (rs.next()) {
            CurrentState currentState = new CurrentState();
            String id_salle = (String) rs.get(1);
            String id_theme = (String) rs.get(2);
            boolean isFree = (boolean) rs.get(3);

            currentState.setSalle(getNgContext().findById(id_salle, Salle.class));
            if (id_theme == null) {
                currentState.setTheme(null);
            } else {
                Theme theme = getNgContext().findById(id_theme, Theme.class);
                theme.setImageThemes(getNgContext().findWhere(ImageTheme.class, "id_theme = '" + id_theme + "'"));
                currentState.setTheme(theme);
            }
            currentState.setFree(isFree);
            currentStates.add(currentState);
        }
        return currentStates;
    }
}
