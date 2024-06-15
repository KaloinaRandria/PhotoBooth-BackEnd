package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Materiel;
import org.photobooth.restapi.service.MaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/materiel")
public class MaterielController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(MaterielController.class.getName());

    /*
    desc : maka ny materiel rehetra
    method : get
    url : .../materiel/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllMateriel() {
        try (MaterielService materielService = new MaterielService()) {
            List<Materiel> materiels = materielService.getAllMateriel();
            ApiResponse apiResponse = new ApiResponse(true, materiels, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
 desc : get by id
 method : get
 url (example) : .../materiel/MAT_2
  */
    @GetMapping("/{materialId}")
    public ResponseEntity<ApiResponse> getMatById(@PathVariable String materialId) {
        try (MaterielService materielService = new MaterielService()) {
            Optional<Materiel> materielOptional = materielService.getMaterielById(materialId);
            if (materielOptional.isPresent()) {
                Materiel materiel = materielOptional.get();
                ApiResponse response = new ApiResponse(true, materiel, null);
                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "Materiel not found : " + materialId;
                ApiResponse response = new ApiResponse(false, null, errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }


    /*
   desc : mi_inserte materiel vaovao anaty base de donne
   method : post
   url : .../materiel/save
   body (example) :
    {
        "quantite": 99,
        "intitule": "noov aaa",
        "prix": 99999.0,
    }
    */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createMateriel(@RequestBody Materiel materiel) {
        try (MaterielService materielService = new MaterielService()) {
            String idMatCreated = materielService.save(materiel);
            ApiResponse response = new ApiResponse(true, materiel, "done");
            logger.info("new Materiel inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
  desc : update
  method : put
  url : .../materiel/update
  body (example) :
   {
"id_materiel": "MAT_1",
"quantite": 99,
"intitule": "noov aaa",
"prix": 99999.0,
}
   */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateMateriel(@RequestBody Materiel materiel) {
        try (MaterielService materielService = new MaterielService()) {
            materielService.update(materiel);
            ApiResponse response = new ApiResponse(true, materiel, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
   desc : delete
   method : delete
   url (example) : .../materiel/delete/MAT_2
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMat(@PathVariable String id) {
        try (MaterielService materielService = new MaterielService()) {
            materielService.delete(id);
            ApiResponse response = new ApiResponse(true, null, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
