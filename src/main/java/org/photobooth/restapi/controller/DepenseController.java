package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.Depense;
import org.photobooth.restapi.model.Membre;
import org.photobooth.restapi.model.Reservation;
import org.photobooth.restapi.service.ClientService;
import org.photobooth.restapi.service.DepenseService;
import org.photobooth.restapi.service.MembreService;
import org.photobooth.restapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/depense")


public class DepenseController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(DepenseController.class.getName());

 /*
    desc : maka ny depense rehetra
    method : get
    url : .../depense/all
     */
  @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDepense(){
        try (DepenseService depenseService = new DepenseService()) {
            List<Depense>depenses = depenseService.getAllDepense();
            ApiResponse apiResponse = new ApiResponse(true,depenses, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
  
  /*
 desc : save
 method : post
 url : .../depense/save
 body (example) :
  {
            "montant":tantandraza,
            "date_insertion": "jj/mm/aaaa",
            "libele":anarana,
  }
  */
 @PostMapping("/save")
    public ResponseEntity<ApiResponse> createDepense(@RequestBody Depense dep) {
        try (DepenseService depenseService = new DepenseService()) {
            String idDepCreated = depenseService.save(dep);
            ApiResponse response = new ApiResponse(true,idDepCreated, "done");
            logger.info("new Client inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
           /*
 desc : update
 method : put
 url : .../depense/update
 body (example) :
  {
            "montant":tantandraza,
            "date_insertion": "jj/mm/aaaa",
            "libele":anarana,
  }
  */

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateDepense(@RequestBody Depense depense) {
        try (DepenseService depenseService = new DepenseService()) {
            depenseService.update(depense);
            ApiResponse response = new ApiResponse(true,depense,"done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

        /*desc : get by id
method : get
url (example) : .../depense/1
*/
     @GetMapping("/{Id}")
    public ResponseEntity<ApiResponse> getDepenseById(@PathVariable String DepenseId) {
        try (DepenseService depenseService = new DepenseService()) {
            Optional<Depense> depenseOptional = depenseService.getDepenseById(DepenseId);
            if (depenseOptional.isPresent()){
                Depense depense=depenseOptional.get();
                ApiResponse response = new ApiResponse(true,depense,null);
                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "depense not found : " + DepenseId;
                ApiResponse response = new ApiResponse(false, null, errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

     /*desc : delete
    method : delete
    url (example) : .../reservation/delete/1    
    */

     @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteDepense(@PathVariable String id) {
        try (DepenseService depenseService = new DepenseService()) {
            depenseService.delete(id);
            ApiResponse response = new ApiResponse(true,id, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }


}
