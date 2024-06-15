package org.photobooth.restapi.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Membre;
import org.photobooth.restapi.service.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/membre")
public class MembreController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(MembreController.class.getName());

    /*
    desc : maka ny membre rehetra
    method : get
    url : .../membre/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllMembre() {
        try (MembreService membreService = new MembreService()) {
            List<Membre> membres = membreService.getAllMembre();
            ApiResponse response = new ApiResponse(true, membres, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
    desc : mi_inserte Membre vaovao anaty base de donne
    method : post
    url : .../membre/save
    body (example) :
    {
        "role": {
            "id_role": "ROLE_2"
        },
        "nom": "Anderson",
        "prenom": "Tonny",
        "date_de_naissance": "2004-04-07",
        "username": "tonny01",
        "mail": "tonny@gmail.com",
        "mot_de_passe": "tonny01",
        "date_embauche": "2024-05-07",
        "poste": {
            "id_poste": "POSTE_1"
        }
    }
     */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createMember(@RequestBody Membre membre) {
        try (MembreService membreService = new MembreService()) {
            String idMembreCreated = membreService.insertMembre(membre);
            ApiResponse response = new ApiResponse(true, idMembreCreated, "done");
            logger.info("new member inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
    desc : connection ana Membre par username et mot de passe
    method : post
    httt::--zdzdzjzd/membre/connect
    url : .../membre/connect
    body (example) :
    {
        "username": "tonny01",
        "mot_de_passe": "tonny01"
    }
     */
    @PostMapping("/connect")
    public ResponseEntity<ApiResponse> connect(@RequestBody Membre membre, HttpServletResponse response) {
        try (MembreService membreService = new MembreService()) {
            Membre membre1 = membreService.connect(membre);

            Cookie cookie = new Cookie("mbr_auth", membre1.getId_membre());
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            ApiResponse responseApi = new ApiResponse(true, membre1, "done");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseApi);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
    desc : maka membre @alalan idMembre
    method : get
    url (example) : .../membre/MBR_1
    */
    @GetMapping("/{membreId}")
    public ResponseEntity<ApiResponse> getMembreById(@PathVariable String membreId) {
        try (MembreService membreService = new MembreService()) {
            Optional<Membre> optionalUser = membreService.getMembreById(membreId);
            if (optionalUser.isPresent()) {
                Membre membre = optionalUser.get();
                ApiResponse response = new ApiResponse(true, membre, null);
                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "Member not found : " + membreId;
                ApiResponse response = new ApiResponse(false, null, errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
    desc : update membre selon l id de la membre dans l attribut
    method : put
    url : .../membre/update
    body (example) :
    {
        "id_membre": "MBR_3",
        "role":{"id_role": "ROLE_1"},
        "nom": "Anderson",
        "prenom": "Tonny Bryan",
        "date_de_naissance": "2004-04-07",
        "username": "tonny01",
        "mail": "tonny@gmail.com",
        "mot_de_passe": "tonny01",
        "date_embauche": "2024-05-07",
        "poste":{"id_poste": "POSTE_2"}
    }
    */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateMember(@RequestBody Membre updatedMembre) {
        try (MembreService membreService = new MembreService()) {
            membreService.updateUser(updatedMembre);
            ApiResponse response = new ApiResponse(true, null, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
