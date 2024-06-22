package org.photobooth.restapi.controller;

import org.entityframework.dev.ApiResponse;
import org.photobooth.restapi.model.Role;
import org.photobooth.restapi.model.ValueRange;
import org.photobooth.restapi.service.RoleService;
import org.photobooth.restapi.service.ValueRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tarif")
public class ValueRangeController {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Logger logger = Logger.getLogger(ValueRangeController.class.getName());

    /* url : tarif/all */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllValue() {
        try (ValueRangeService valueRangeService = new ValueRangeService()) {
            List<ValueRange> valueRanges = valueRangeService.getAll();
            ApiResponse apiResponse = new ApiResponse(true, valueRanges, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.Of(e));
        }
    }
}
