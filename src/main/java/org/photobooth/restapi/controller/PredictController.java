package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Poste;
import org.photobooth.restapi.model.ai.MonthPredict;
import org.photobooth.restapi.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/predict")
public class PredictController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(Poste.class.getName());

    @GetMapping("/nextMonth")
    public ResponseEntity<ApiResponse> getProfit(){
        try (PredictionService predictionService = new PredictionService()) {
            MonthPredict data = predictionService.getNextMonthPrediction();
            ApiResponse apiResponse = new ApiResponse(true,data, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
