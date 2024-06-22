package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.entityframework.dev.GenericObject;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.service.ClientService;
import org.photobooth.restapi.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/stat")
public class StatController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(ClientController.class.getName());

    @GetMapping("/financial/{annee}")
    public ResponseEntity<ApiResponse> getFinancialStat(@PathVariable int annee){
        try (StatService statService = new StatService()) {
            GenericObject data = statService.getFinancialStat(annee);
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
