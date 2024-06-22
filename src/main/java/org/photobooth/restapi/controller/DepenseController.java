package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.Depense;
import org.photobooth.restapi.model.Membre;
import org.photobooth.restapi.model.stat.AllTimeUsedMateriel;
import org.photobooth.restapi.model.stat.DepenseStat;
import org.photobooth.restapi.service.ClientService;
import org.photobooth.restapi.service.DepenseService;
import org.photobooth.restapi.service.MaterielService;
import org.photobooth.restapi.service.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;
@RestController
@RequestMapping("/depense")
public class DepenseController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(DepenseController.class.getName());

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
            depenseService.save(dep);
            ApiResponse response = new ApiResponse(true,"okay", "done");
            logger.info("new Depense inserted");
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
    public ResponseEntity<ApiResponse> updateClient(@RequestBody Depense depense) {
        try (DepenseService depenseService = new DepenseService()) {
            depenseService.update(depense);
            ApiResponse response = new ApiResponse(true,depense,"done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/stat/{annee}")
    public ResponseEntity<ApiResponse> getStat(@PathVariable int annee) {
        try (DepenseService depenseService = new DepenseService()) {
            DepenseStat stat = depenseService.getDepense(annee);
            ApiResponse apiResponse = new ApiResponse(true, stat, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
