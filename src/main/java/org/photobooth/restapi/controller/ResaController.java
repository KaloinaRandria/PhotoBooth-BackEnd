package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.entityframework.dev.Metric;
import org.photobooth.restapi.http.data.ReservationInterval;
import org.photobooth.restapi.model.Poste;
import org.photobooth.restapi.model.Role;
import org.photobooth.restapi.service.ReservationService;
import org.photobooth.restapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/resa")
public class ResaController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(Poste.class.getName());

    @PostMapping("/available")
    public ResponseEntity<ApiResponse> check(@RequestBody ReservationInterval interval) {
        try(ReservationService reservationService = new ReservationService())  {
            boolean test = reservationService.isAvailable(interval);
            ApiResponse apiResponse = new ApiResponse(true, test, "");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
