package org.photobooth.restapi.service;

import org.photobooth.restapi.model.Notification;

import java.util.List;

public class NotificationService extends Service {

    public NotificationService() {
        super();
    }

    public List<Notification> getAll() throws Exception {
        return getNgContext().findWhen(Notification.class, "ORDER BY action_date desc");
    }

}
