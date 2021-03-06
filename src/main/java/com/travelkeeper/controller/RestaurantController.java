package com.travelkeeper.controller;

import com.travelkeeper.domain.Restaurant;
import com.travelkeeper.service.IRestaurantService;
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
 * Restaurant controller
 *
 * Created by netocris on 04/09/2018
 */
@RestController
@RequestMapping(value = "restaurants", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
@Api(value = "Restaurants", description = "REST API for Restaurant", tags = { "Restaurant" })
public class RestaurantController {

    private IRestaurantService service;

    @Autowired
    public RestaurantController(final IRestaurantService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Get paginated list of restaurants", httpMethod = "GET", tags = { "Restaurant" })
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Page<Restaurant>> getAll(){
        try {
            log.debug("getting all restaurants");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.getAll());
        } catch (Exception ex){
            log.error("Error getting all restaurants", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get restaurant", httpMethod = "GET", tags = { "Restaurant" })
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Restaurant> getById(@PathVariable("id") final Long id){
        try {
            log.info("getting restaurant with id " + id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.getById(id));
        } catch (Exception ex){
            log.error("Error getting restaurant with id " + id , ex);
            throw new InternalServerErrorException(ex);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create restaurant", httpMethod = "POST", tags = { "Restaurant" })
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Restaurant> create(@Valid @RequestBody final Restaurant entity){
        try {
            log.info("creating restaurant");
            this.service.save(entity);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(entity);
        } catch (Exception ex){
            log.error("Error creating restaurant", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete restaurant", httpMethod = "DELETE", tags = { "Restaurant" })
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Void> remove(@PathVariable("id") final Long id){
        try {
            log.info("deleting restaurant with id " + id);
            this.service.delete(id);
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .build();
        } catch (Exception ex){
            log.error("Error deleting restaurant " + id , ex);
            throw new InternalServerErrorException(ex);
        }
    }

}
