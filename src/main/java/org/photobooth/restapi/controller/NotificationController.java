package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Notification;
import org.photobooth.restapi.model.Salle;
import org.photobooth.restapi.service.NotificationService;
import org.photobooth.restapi.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/notif")
public class NotificationController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(NotificationController.class.getName());

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllNotif() {
        try (NotificationService notificationService = new NotificationService()) {
            List<Notification> notifications = notificationService.getAll();
            ApiResponse apiResponse = new ApiResponse(true, notifications, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
