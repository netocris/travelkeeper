package com.travelkeeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelkeeper.domain.Restaurant;
import com.travelkeeper.service.IRestaurantService;
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
public class RestaurantControllerIT {

    private static final long ID = 0L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IRestaurantService service;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() throws Exception {

        final Restaurant entity = createMock();
        this.service.save(entity);

        this.mvc.perform(get("/restaurants")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.numberOfElements", greaterThanOrEqualTo(1)));

        this.service.delete(ID);

    }

    @Test
    public void getById() throws Exception {

        final Restaurant entity = createMock();
        this.service.save(entity);

        this.mvc.perform(get("/restaurants/{id}", ID)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        this.service.delete(ID);

    }

    @Test
    public void create() throws Exception {

        final Restaurant entity = new Restaurant();
        entity.setId(ID);
        entity.setName("teste");

        this.mvc.perform(post("/restaurants")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isCreated());

        this.service.delete(ID);

    }

    @Test
    public void remove() throws Exception {

        final Restaurant entity = createMock();
        this.service.save(entity);

        this.mvc.perform(delete("/restaurants/{id}", ID)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isGone());

    }

    private Restaurant createMock(){

        final Restaurant entity = new Restaurant();
        entity.setId(ID);
        entity.setName("teste");

        return entity;

    }

}