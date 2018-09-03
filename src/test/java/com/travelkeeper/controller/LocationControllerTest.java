package com.travelkeeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelkeeper.domain.Location;
import com.travelkeeper.service.ILocationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ILocationService service;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() throws Exception {

        final Location entity = new Location();
        entity.setId(1L);
        entity.setName("teste");

        final Page<Location> page = new PageImpl<>(singletonList(entity));

        when(service.getAll()).thenReturn(page);

        this.mvc.perform(get("/locations")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("teste")))
                .andExpect(jsonPath("$.numberOfElements", is(1)));

        verify(this.service, times(1)).getAll();
        verifyNoMoreInteractions(this.service);

    }

    @Test
    public void getById() throws Exception {

        final Location entity = new Location();
        entity.setId(1L);
        entity.setName("teste");

        when(service.getById(anyLong())).thenReturn(entity);

        this.mvc.perform(get("/locations/{id}", 1L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("teste")));

        verify(this.service, times(1)).getById(anyLong());
        verifyNoMoreInteractions(this.service);

    }

    @Test
    public void create() throws Exception {

        final Location entity = new Location();
        entity.setId(1L);
        entity.setName("teste");

        this.mvc.perform(post("/locations")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isCreated());

        verify(this.service, times(1)).save(any());
        verifyNoMoreInteractions(this.service);

    }

    @Test
    public void remove() throws Exception {

        // mock service method request
        doNothing().when(this.service).delete(anyLong());

        this.mvc.perform(delete("/locations/{id}", 1L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isGone());

        verify(this.service, times(1)).delete(anyLong());
        verifyNoMoreInteractions(this.service);

    }

}