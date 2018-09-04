package com.travelkeeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelkeeper.domain.Location;
import com.travelkeeper.service.ILocationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerIT {

    private static final long ID = 0L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ILocationService service;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() throws Exception {

        createLocationTest();

        this.mvc.perform(get("/locations")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.numberOfElements", greaterThanOrEqualTo(1)));

        removeLocationTest();

    }

    @Test
    public void getById() throws Exception {

        createLocationTest();

        this.mvc.perform(get("/locations/{id}", ID)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        removeLocationTest();

    }

    @Test
    public void create() throws Exception {

        final Location entity = new Location();
        entity.setId(ID);
        entity.setName("teste");

        this.mvc.perform(post("/locations")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isCreated());

        removeLocationTest();

    }

    @Test
    public void remove() throws Exception {

        createLocationTest();

        this.mvc.perform(delete("/locations/{id}", ID)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isGone());

    }

    private void createLocationTest(){
        final Location entity = new Location();
        entity.setId(ID);
        entity.setName("teste");

        this.service.save(entity);
    }

    private void removeLocationTest(){
        this.service.delete(ID);
    }

}