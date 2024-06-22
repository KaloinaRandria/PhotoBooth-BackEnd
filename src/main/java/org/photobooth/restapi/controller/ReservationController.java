package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.Reservation;
import org.photobooth.restapi.service.ClientService;
import org.photobooth.restapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rservation")
public class ReservationController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(ClientController.class.getName());

    /*
    desc : maka ny client rehetra
    method : get
    url : .../reservation/all
     */

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllReservation(){
        try (ReservationService reservationService = new ReservationService()) {
            List<Reservation> reservations = reservationService.getAllReservation();
            ApiResponse apiResponse = new ApiResponse(true,reservations, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*desc : get by id
method : get
url (example) : .../reservation/Res_2
*/
    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponse> getReservationById(@PathVariable String reservationId) {
        try (ReservationService reservationService = new ReservationService()) {
            Optional<Reservation> reservationOptional = reservationService.getReservationById(reservationId);
            if (reservationOptional.isPresent()){
                Reservation reservation=reservationOptional.get();
                ApiResponse response = new ApiResponse(true,reservation,null);
                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "reservation not found : " + reservationId;
                ApiResponse response = new ApiResponse(false, null, errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*desc : mi_inserte reservation vaovao anaty base de donne
   method : post
   url : .../reservation/save
   body (example) :
   {
            "date_reservation":tantandraza,
           "date_reservation": "noov aaa",
           "date_reservee": tatandraza@gmail.com,
           "id_client":CLT_1(cle etrangere),
           "id_sevice":SERV_1,(cle etrangere),
           "heure_debut":HH/MM,
           "heure_Fin":HH/MM,
           "prix":2000.00,
   }
   */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createReservation(@RequestBody Reservation reservation ) {
        try (ReservationService reservationService = new ReservationService()){
            String idReservationCreated = reservationService.save(reservation);
            ApiResponse response = new ApiResponse(true,idReservationCreated, "done");
            logger.info("new reservation inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
desc : update
method : put
url : .../reservation/update
body (example) :
{         "id_Reservation"=Res_1,
           "date_reservation": "noov aaa",
           "date_reservee": tatandraza@gmail.com,
           "id_client":CLT_1(cle etrangere),
           "id_sevice":SERV_1,(cle etrangere),
           "heure_debut":HH/MM,
           "heure_Fin":HH/MM,
           "prix":2000.00,
*/
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateReservation(@RequestBody Reservation reservation) {
        try (ReservationService reservationService = new ReservationService()) {
            reservationService.update(reservation);
            ApiResponse response = new ApiResponse(true,reservation,"done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
    /*desc : delete
    method : delete
    url (example) : .../reservation/delete/Res_2
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteReservation(@PathVariable String id) {
        try (ReservationService reservationService = new ReservationService()) {
            reservationService.delete(id);
            ApiResponse response = new ApiResponse(true,id, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

}
