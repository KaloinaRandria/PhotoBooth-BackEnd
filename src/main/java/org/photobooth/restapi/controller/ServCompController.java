package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.ServComp;
import org.photobooth.restapi.service.ServCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/service")
public class ServCompController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(ServCompController.class.getName());


    /*
    desc : maka ny service rehetra
    method : get
    url : .../service/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllService() {
        try (ServCompService servCompService = new ServCompService()) {
            List<ServComp> servComps = servCompService.getAllService();
            ApiResponse apiResponse = new ApiResponse(true, servComps, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
    desc : mi_inserte service vaovao anaty base de donne
    method : post
    url : .../service/save
    body (example) :
   {
    "intitule": "tournage",
    "prix_unitaire": 1000000.0
    }
    */

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createService(@RequestBody ServComp servComp) {
        try (ServCompService servCompService = new ServCompService()) {
            String idServiceCreated = servCompService.insertService(servComp);
            ApiResponse response = new ApiResponse(true, idServiceCreated, "done");
            logger.info("new Service inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
    desc : update
    method : put
    url : .../service/update
    body (example) :
    {
    "id_comp_service": "SERV_1",
    "intitule": "tournage",
    "prix_unitaire": 1000000.0
    }
    */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateService(@RequestBody ServComp updatedService) {
        try (ServCompService servCompService = new ServCompService()) {
            servCompService.updateService(updatedService);
            ApiResponse response = new ApiResponse(true, updatedService, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
   desc : delete
   method : delete
   url (example) : .../service/delete/SERV_1
    */
    @DeleteMapping("/delete/{idService}")
    public ResponseEntity<ApiResponse> deleteService(@PathVariable String idService) {
        try (ServCompService servCompService = new ServCompService()) {
            servCompService.deleteService(idService);
            ApiResponse response = new ApiResponse(true, null, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
