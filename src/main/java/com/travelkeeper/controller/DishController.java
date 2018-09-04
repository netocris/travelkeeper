package com.travelkeeper.controller;

import com.travelkeeper.domain.Location;
import com.travelkeeper.service.ILocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Dish controller
 *
 * Created by netocris on 04/09/2018
 */
@RestController
@RequestMapping(value = "dishes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
@Api(value = "Dish", description = "REST API for Dish", tags = { "Dish" })
public class DishController {

    private ILocationService service;

    @Autowired
    public DishController(final ILocationService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Get paginated list of dishes", httpMethod = "GET", tags = { "Dish" })
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Page<Location>> getAll(){
        try {
            log.debug("getting all dishes");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.getAll());
        } catch (Exception ex){
            log.error("Error getting all dishes", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "get dish", httpMethod = "GET", tags = { "Dish" })
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Location> getById(@PathVariable("id") final Long id){
        try {
            log.info("getting dish with id " + id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.getById(id));
        } catch (Exception ex){
            log.error("Error getting dish with id " + id , ex);
            throw new InternalServerErrorException(ex);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create dish", httpMethod = "POST", tags = { "Dish" })
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Location> create(@Valid @RequestBody final Location entity){
        try {
            log.info("creating dish");
            this.service.save(entity);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(entity);
        } catch (Exception ex){
            log.error("Error creating dish", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete dish", httpMethod = "DELETE", tags = { "Dish" })
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Void> remove(@PathVariable("id") final Long id){
        try {
            log.info("deleting dish with id " + id);
            this.service.delete(id);
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .build();
        } catch (Exception ex){
            log.error("Error deleting dish " + id , ex);
            throw new InternalServerErrorException(ex);
        }
    }

}
