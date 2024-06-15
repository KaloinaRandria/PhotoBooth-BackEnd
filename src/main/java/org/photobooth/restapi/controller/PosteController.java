package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Poste;
import org.photobooth.restapi.model.Role;
import org.photobooth.restapi.service.PosteService;
import org.photobooth.restapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/poste")
public class PosteController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(Poste.class.getName());

    /*
    desc : maka ny poste rehetra
    method : get
    url : .../poste/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPoste() {
        try (PosteService posteService = new PosteService()) {
            List<Poste> postes = posteService.getAllPoste();
            ApiResponse apiResponse = new ApiResponse(true, postes, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
 desc : get by id
 method : get
 url (example) : .../poste/POSTE_2
  */
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse> getPosteById(@PathVariable String postId) {
        try (PosteService posteService = new PosteService()) {
            Optional<Poste> optionalPoste = posteService.getPosteById(postId);
            if (optionalPoste.isPresent()) {
                Poste poste = optionalPoste.get();
                ApiResponse response = new ApiResponse(true, poste, null);
                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "Poste not found : " + postId;
                ApiResponse response = new ApiResponse(false, null, errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
  desc : mi_inserte poste vaovao anaty base de donne
  method : post
  url : .../poste/save
  body (example) :
   {
    "intitule": "Level1"
    }
   */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createPoste(@RequestBody Poste poste) {
        try (PosteService posteService = new PosteService()) {
            String idPosteCreated = posteService.save(poste);
            ApiResponse response = new ApiResponse(true, poste, "done");
            logger.info("new Poste inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
 desc : update
 method : put
 url : .../role/update
 body (example) :
  {
"id_poste": "POSTE_1",
"intitule": "Level1"
}
  */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updatePoste(@RequestBody Role role) {
        try (RoleService roleService = new RoleService()) {
            roleService.update(role);
            ApiResponse response = new ApiResponse(true, role, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
   desc : delete
   method : delete
   url (example) : .../poste/delete/ROLE_1
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable String id) {
        try (PosteService posteService = new PosteService()) {
            posteService.delete(id);
            ApiResponse response = new ApiResponse(true, null, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
