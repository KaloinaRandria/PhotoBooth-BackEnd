package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Categorie_theme;
import org.photobooth.restapi.service.Categorie_theme_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/categ")
public class CategThemeController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(CategThemeController.class.getName());

    /*
    desc : maka ny categ theme rehetra
    method : get
    url : .../categ/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCateg() {
        try (Categorie_theme_service categorieThemeService = new Categorie_theme_service()) {
            List<Categorie_theme> categorieThemes = categorieThemeService.getAll();
            ApiResponse apiResponse = new ApiResponse(true, categorieThemes, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
   desc : mi_inserte categorie theme vaovao anaty base de donne
   method : post
   url : .../categ/save
   body (example) :
    {
    "intitule": "dein letiee"
    }
    */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createCateg(@RequestBody Categorie_theme categorie_theme) {
        try (Categorie_theme_service categorieThemeService = new Categorie_theme_service()) {
            String idCategCreated = categorieThemeService.save(categorie_theme);
            ApiResponse response = new ApiResponse(true, categorie_theme, "done");
            logger.info("new Categ inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
   desc : update
   method : put
   url : .../categ/update
   body (example) :
    {
    "id_categorie_theme": "CAT_TH_2",
    "intitule": "dein letiee"
    }
    */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateCateg(@RequestBody Categorie_theme categorie_theme) {
        try (Categorie_theme_service categorieThemeService = new Categorie_theme_service()) {
            categorieThemeService.update(categorie_theme);
            ApiResponse response = new ApiResponse(true, categorie_theme, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
   desc : delete
   method : delete
   url (example) : .../categ/delete/CAT_TH_2
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCateg(@PathVariable String id) {
        try (Categorie_theme_service categorieThemeService = new Categorie_theme_service()) {
            categorieThemeService.delete(id);
            ApiResponse response = new ApiResponse(true, null, "done");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
