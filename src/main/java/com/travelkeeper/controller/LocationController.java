package com.travelkeeper.controller;

import com.travelkeeper.domain.Location;
import com.travelkeeper.service.ILocationService;
import com.travelkeeper.service.LocationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @ApiOperation(value = "Return a list of locations", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request")})
    public ResponseEntity<Page<Location>> getAll(){
        try {
            log.debug("getting all locations");
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

    @PostMapping
    @ApiOperation(value = "Create location", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request")})
    public ResponseEntity<Location> create(@Valid @RequestBody final Location entity){
        try {
            log.info("creating location");
            this.service.save(entity);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(entity);
        } catch (Exception ex){
            log.error("Error creating location", ex);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

}
