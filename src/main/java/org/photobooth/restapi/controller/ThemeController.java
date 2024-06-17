package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.entityframework.dev.Metric;
import org.photobooth.restapi.http.data.MaterielDataList;
import org.photobooth.restapi.model.Theme;
import org.photobooth.restapi.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
@RequestMapping("/theme")
public class ThemeController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(ThemeController.class.getName());

    /*
    desc : maka ny theme rehetra
    method : get
    url : .../theme/all
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllTheme() {
        try (ThemeService themeService = new ThemeService()) {
            List<Theme> themes = themeService.getAllTheme();
            ApiResponse apiResponse = new ApiResponse(true, themes, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
    desc : get by id
    method : get
    url (example) : .../theme/TH_2
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getThemeById(@PathVariable String id) {
        try (ThemeService themeService = new ThemeService()) {
            Optional<Theme> optionalTheme = themeService.getThemeById(id);
            if (optionalTheme.isPresent()) {
                Theme theme = optionalTheme.get();
                ApiResponse response = new ApiResponse(true, theme, null);
                return ResponseEntity.ok(response);
            } else {
                String errorMessage = "theme not found : " + id;
                ApiResponse response = new ApiResponse(false, null, errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    /*
    desc : mi_inserte theme avec sary ao anaty base
    method : post
    url : .../theme/save
    body (example) :
    Require : FormData
    Part 1 : image , data : ilay sary , accept : image/*
    Part 2 : data , data :
    Part 3 : materiel , materiel :
     */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> form(
            @RequestPart("image") MultipartFile file,
            @RequestPart("data") Theme theme,
            @RequestPart("materiel") MaterielDataList mdl) throws IllegalAccessException, SQLException {


        if (file.isEmpty()) {
            return ResponseEntity.internalServerError().body(new ApiResponse(false, null, "Empty file"));
        }
        try (ThemeService themeService = new ThemeService()) {
            themeService.save(file, theme, mdl, applicationContext);
            ApiResponse response = new ApiResponse(true, theme, "done");
            logger.info("new Theme inserted");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
