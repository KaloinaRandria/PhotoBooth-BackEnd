package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.entityframework.dev.GenericObject;
import org.photobooth.restapi.model.Client;
import org.photobooth.restapi.model.stat.*;
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

    @GetMapping("/client/{idClient}")
    public ResponseEntity<ApiResponse> getResaStatYear(@PathVariable String idClient){
        try (StatService statService = new StatService()) {
            GenericObject data = statService.getAllTimeClientStat(statService.getNgContext(),idClient);
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/client/top/{limit}")
    public ResponseEntity<ApiResponse> getTopClient(@PathVariable int limit){
        try (StatService statService = new StatService()) {
            List<ClientStat> data = statService.getTopClient(limit);
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/profit")
    public ResponseEntity<ApiResponse> getProfit(){
        try (StatService statService = new StatService()) {
            GenericObject data = statService.allProfit();
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @PostMapping("/service/range")
    public ResponseEntity<ApiResponse> getMostService(@RequestBody MostServiceStat mostServiceStat){
        try (StatService statService = new StatService()) {
            MostServiceStat data = statService.getMostService(mostServiceStat);
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @PostMapping("/finance/range")
    public ResponseEntity<ApiResponse> getFinanceRange(@RequestBody MostServiceStat mostServiceStat){
        try (StatService statService = new StatService()) {
            GenericObject data = statService.getFinancial(mostServiceStat.getStart(), mostServiceStat.getEnd());
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }

    @GetMapping("/mat")
    public ResponseEntity<ApiResponse> getMatStat(){
        try (StatService statService = new StatService()) {
            MatStat data = statService.getMatStat();
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
