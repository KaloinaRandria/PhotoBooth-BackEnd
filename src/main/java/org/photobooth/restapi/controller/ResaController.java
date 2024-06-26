package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.entityframework.dev.GenericObject;
import org.entityframework.dev.Metric;
import org.photobooth.restapi.http.data.ReservationInterval;
import org.photobooth.restapi.http.data.Shedule;
import org.photobooth.restapi.model.Poste;
import org.photobooth.restapi.model.Reservation;
import org.photobooth.restapi.model.Role;
import org.photobooth.restapi.service.ReservationService;
import org.photobooth.restapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Metric.print(interval);
            Object test = reservationService.isAvailable(interval);
            ApiResponse apiResponse = new ApiResponse(true, test, "");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createResa(@RequestBody Reservation resa) {
        try (ReservationService reservationService = new ReservationService()) {
            String idCreated = reservationService.save(resa);
            ApiResponse response = new ApiResponse(true, resa, "done");
            logger.info("new Reservation inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<ApiResponse> confirm(@PathVariable String id) {
        try (ReservationService reservationService = new ReservationService()) {
            boolean res = reservationService.confirm(id);
            ApiResponse response = new ApiResponse(true, res, "done");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ApiResponse> cancel(@PathVariable String id) {
        try (ReservationService reservationService = new ReservationService()) {
            GenericObject res = reservationService.cancel(id);
            ApiResponse response = new ApiResponse((Boolean) res.getAttribute("flag"), null, (String) res.getAttribute("message"));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/shedule")
    public ResponseEntity<ApiResponse> getShedule() {
        try (ReservationService reservationService = new ReservationService()) {
            List<Shedule> sheduleList = reservationService.getAppShedule();
            ApiResponse apiResponse = new ApiResponse(true, sheduleList, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try (ReservationService reservationService = new ReservationService()) {
            List<Reservation> reservations = reservationService.getAllReservation();
            ApiResponse apiResponse = new ApiResponse(true, reservations, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/alldone")
    public ResponseEntity<ApiResponse> getAllDone() {
        try (ReservationService reservationService = new ReservationService()) {
            List<Reservation> reservations = reservationService.getAllReservationDone();
            ApiResponse apiResponse = new ApiResponse(true, reservations, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
