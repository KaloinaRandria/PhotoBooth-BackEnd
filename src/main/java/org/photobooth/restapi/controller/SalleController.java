package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Salle;
import org.photobooth.restapi.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/salle")
public class SalleController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(SalleController.class.getName());

    /*
    desc : maka ny salle rehetra
    method : get
    url : .../salle/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllSalle() {
        try (SalleService salleService = new SalleService()) {
            List<Salle> salles = salleService.getAllSalle();
            ApiResponse apiResponse = new ApiResponse(true, salles, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
 desc : mi_inserte salle vaovao anaty base de donne
 method : post
 url : .../salle/save
 body (example) :
  {
"numero": 452,
}
  */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createSalle(@RequestBody Salle salle) {
        try (SalleService salleService = new SalleService()) {
            String idsalleCreated = salleService.insertSalle(salle);
            ApiResponse response = new ApiResponse(true, salle, "done");
            logger.info("new salle inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
desc : update
method : put
url : .../salle/update
body (example) :
{
"id_salle": "SALLE_1",
"numero": 452,
}
*/
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateSalle(@RequestBody Salle salle) {
        try (SalleService salleService = new SalleService()) {
            salleService.update(salle);
            ApiResponse response = new ApiResponse(true, salle, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
   desc : delete
   method : delete
   url (example) : .../salle/delete/SALLE_1
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSalle(@PathVariable String id) {
        try (SalleService salleService = new SalleService()) {
            salleService.delete(id);
            ApiResponse response = new ApiResponse(true, null, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
