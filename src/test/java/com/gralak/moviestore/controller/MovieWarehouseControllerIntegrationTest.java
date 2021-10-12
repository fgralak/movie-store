package com.gralak.moviestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gralak.moviestore.movie.Movie;
import com.gralak.moviestore.moviewarehouse.MovieWarehouse;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", roles = {"USER", "EMPLOYEE"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MovieWarehouseControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddAndGetMovieWarehouseById() throws Exception
    {
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        //post new movie-warehouse relation
        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse)))
                .andExpect(status().is(201));

        movie.setId(1L);
        warehouse.setId(1L);
        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse)))
                .andExpect(status().is(201));

        //get warehouse by id
        String actual = mockMvc
                .perform(get("/api/movie-warehouse?movieId=1&warehouseId=1"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        String expected = asJsonString(movieWarehouse);

        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateMovieWarehouse() throws Exception
    {
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        movie.setId(1L);
        warehouse.setId(1L);

        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);
        MovieWarehouse update = new MovieWarehouse(movie, warehouse, 20);

        update.setId(movieWarehouse.getId());

        //post new movie-warehouse relation
        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse)))
                .andExpect(status().is(201));

        //update relation
        mockMvc.perform(put("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(update)))
                .andExpect(status().is(200));

        //get updated relation by id
        String actual = mockMvc
                .perform(get("/api/movie-warehouse?movieId=1&warehouseId=1"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        String expected = asJsonString(update);

        assertEquals(expected, actual);
    }

    @Test
    void shouldDeleteMovieAndGetList() throws Exception
    {
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        Warehouse warehouse2 = new Warehouse("Wielkopolska 19", "Poznan", "00-210");

        movie.setId(1L);
        warehouse.setId(1L);
        warehouse2.setId(2L);

        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);
        MovieWarehouse movieWarehouse2 = new MovieWarehouse(movie, warehouse2, 20);


        //post new movie-warehouse relations
        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse2)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse2)))
                .andExpect(status().is(201));

        //delete relation with movieId=1 and warehouseId=1
        mockMvc.perform(delete("/api/movie-warehouse?movieId=1&warehouseId=1"))
                .andExpect(status().is(200));

        //get all relations
        String actual = mockMvc
                .perform(get("/api/movie-warehouse/all"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        String expected = asJsonString(Collections.singletonList(movieWarehouse2));

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllMoviesFromWarehouseId() throws Exception
    {
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        Movie movie2 = new Movie("The Lord Of The Rings II", 2002, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        Movie movie3 = new Movie("The Lord Of The Rings III", 2003, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        movie.setId(1L);
        movie2.setId(2L);
        movie3.setId(3L);
        warehouse.setId(1L);

        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);
        MovieWarehouse movieWarehouse2 = new MovieWarehouse(movie2, warehouse, 20);
        MovieWarehouse movieWarehouse3 = new MovieWarehouse(movie3, warehouse, 30);

        //post new movie-warehouse relations
        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie2)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie3)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(warehouse)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse2)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse3)))
                .andExpect(status().is(201));

        //get all movies from warehouse with id=1
        String actual = mockMvc
                .perform(get("/api/movie-warehouse/all-movies?warehouseId=1"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        Map<Long, Integer> map = new HashMap<>();
        map.put(movie.getId(), 10);
        map.put(movie2.getId(), 20);
        map.put(movie3.getId(), 30);

        String expected = asJsonString(map);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllWarehousesFromMovieId() throws Exception
    {
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        Warehouse warehouse2 = new Warehouse("Wielkopolska 19", "Poznan", "00-210");
        Warehouse warehouse3 = new Warehouse("Malopolska 19", "Krakow", "00-654");

        movie.setId(1L);
        warehouse.setId(1L);
        warehouse2.setId(2L);
        warehouse3.setId(3L);

        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);
        MovieWarehouse movieWarehouse2 = new MovieWarehouse(movie, warehouse2, 20);
        MovieWarehouse movieWarehouse3 = new MovieWarehouse(movie, warehouse3, 30);

        //post new movie-warehouse relations
        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(status().is(201));

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

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse2)))
                .andExpect(status().is(201));

        mockMvc.perform(post("/api/movie-warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieWarehouse3)))
                .andExpect(status().is(201));

        //get all warehouses from movie with id=1
        String actual = mockMvc
                .perform(get("/api/movie-warehouse/all-warehouses?movieId=1"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        Map<Long, Integer> map = new HashMap<>();
        map.put(warehouse.getId(), 10);
        map.put(warehouse2.getId(), 20);
        map.put(warehouse3.getId(), 30);

        String expected = asJsonString(map);

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