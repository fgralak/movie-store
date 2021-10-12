package com.gralak.moviestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gralak.moviestore.warehouse.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", roles = {"USER", "EMPLOYEE"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WarehouseControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddAndGetWarehouseById() throws Exception
    {
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        //post new warehouse
        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse)))
                .andExpect(status().is(201));

        //get warehouse by id
        String actual = mockMvc
                .perform(get("/api/warehouse?id=1"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        warehouse.setId(1L);

        String expected = asJsonString(warehouse);

        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateMovie() throws Exception
    {
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        Warehouse update = new Warehouse("Wielkopolska 19", "Poznan", "00-210");

        update.setId(1L);

        //post new warehouse
        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse)))
                .andExpect(status().is(201));

        //update warehouse
        mockMvc.perform(put("/api/warehouse?id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(update)))
                .andExpect(status().is(200));

        //get updated warehouse by id
        String actual = mockMvc
                .perform(get("/api/warehouse?id=1"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        String expected = asJsonString(update);

        assertEquals(expected, actual);
    }

    @Test
    void shouldDeleteMovieAndGetList() throws Exception
    {
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        Warehouse warehouse2 = new Warehouse("Wielkopolska 19", "Poznan", "00-210");
        Warehouse warehouse3 = new Warehouse("Malopolska 19", "Krakow", "00-654");

        //post new warehouses
        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse2)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse3)))
                .andExpect(status().is(201));

        //delete warehouse with id=2
        mockMvc.perform(delete("/api/warehouse?id=2"))
                .andExpect(status().is(200));

        //get all warehouses
        String actual = mockMvc
                .perform(get("/api/warehouse/all"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        warehouse.setId(1L);
        warehouse3.setId(3L);

        String expected = asJsonString(Arrays.asList(warehouse, warehouse3));

        assertEquals(expected, actual);
    }

    public String asJsonString(Object obj)
    {
        try
        {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}