package com.travelkeeper.controller;

import com.travelkeeper.domain.Location;
import com.travelkeeper.service.ILocationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Location service implementation
 *
 * Created by netocris on 27/08/2018
 */
@RestController
@RequestMapping(value = "locations")
@Slf4j
public class LocationController {

    private ILocationService service;

    @Autowired
    public LocationController(final ILocationService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Return a list of modules", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request")})
    public ResponseEntity<Page<Location>> getAll(){
        try {
            log.info("getting all modules");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.getAll());
        } catch (Exception ex){
            log.error("Error getting all modules", ex);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

}
