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
            throw new InternalServerErrorException(ex);
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "get module with id", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request")})
    public ResponseEntity<Location> getById(@PathVariable("id") final Long id){
        try {
            log.info("getting location with id " + id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.getById(id));
        } catch (Exception ex){
            log.error("Error getting location with id " + id , ex);
            throw new InternalServerErrorException(ex);
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
            throw new InternalServerErrorException(ex);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete module with id", httpMethod = "DELETE")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request")})
    public ResponseEntity<Void> remove(@PathVariable("id") final Long id){
        try {
            log.info("deleting location with id " + id);
            this.service.delete(id);
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .build();
        } catch (Exception ex){
            log.error("Error deleting location " + id , ex);
            throw new InternalServerErrorException(ex);
        }
    }

}
