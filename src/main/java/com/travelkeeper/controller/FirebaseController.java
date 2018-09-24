package com.travelkeeper.controller;

import com.travelkeeper.service.google.firebase.IFirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Firebase test controller
 *
 * Created by netocris on 21/09/2018
 */
@RestController
@RequestMapping(value = "firebase", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class FirebaseController {

    private IFirebaseService service;

    @Autowired
    public FirebaseController(final IFirebaseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> getAll(){
        try {
            log.debug("getting all");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.getAll());
        } catch (Exception ex){
            log.error("Error getting all", ex);
            throw new InternalServerErrorException(ex);
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid final String value){
        try {
            log.info("creating");
            this.service.save(value);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(value);
        } catch (Exception ex){
            log.error("Error creating", ex);
            throw new InternalServerErrorException(ex);
        }
    }

}
