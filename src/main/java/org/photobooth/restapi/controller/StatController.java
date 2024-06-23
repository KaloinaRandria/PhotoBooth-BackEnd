package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.entityframework.dev.GenericObject;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.stat.AllTimeThemeStat;
import org.photobooth.restapi.model.stat.ReservationStat;
import org.photobooth.restapi.model.stat.ServiceStat;
import org.photobooth.restapi.service.ClientService;
import org.photobooth.restapi.service.StatService;
import org.photobooth.restapi.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/theme/{id}")
    public ResponseEntity<ApiResponse> getThemeStat(@PathVariable String id){
        try (ThemeService themeService = new ThemeService()) {
            AllTimeThemeStat data = themeService.getStat(id);
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/service/{choice}")
    public ResponseEntity<ApiResponse> getThemeStat(@PathVariable int choice){
        try (StatService statService = new StatService()) {
            ServiceStat data = statService.getServiceStat(choice);
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @PostMapping("/resa")
    public ResponseEntity<ApiResponse> getThemeStat(@RequestBody ReservationStat reservationStat){
        try (StatService statService = new StatService()) {
            ReservationStat data = statService.getResaStat(reservationStat);
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/resa/year/{annee}")
    public ResponseEntity<ApiResponse> getResaStatYear(@PathVariable int annee){
        try (StatService statService = new StatService()) {
            GenericObject data = statService.getResaYearStat(annee);
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
