package com.gralak.moviestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gralak.moviestore.movie.Movie;
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
class MovieControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddAndGetMovieById() throws Exception
    {
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        //post new movie
        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(status().is(201));

        //get movie by id
        String actual = mockMvc
                .perform(get("/api/movie?id=1"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        movie.setId(1L);

        String expected = asJsonString(movie);

        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateMovie() throws Exception
    {
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        Movie update = new Movie("Lotr II", 2002, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 29.99);

        update.setId(1L);

        //post new movie
        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(status().is(201));

        //update movie
        mockMvc.perform(put("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(update)))
                .andExpect(status().is(200));

        //get updated movie by id
        String actual = mockMvc
                .perform(get("/api/movie?id=1"))
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

        Movie movie2 = new Movie("The Lord Of The Rings II", 2002, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        Movie movie3 = new Movie("The Lord Of The Rings III", 2003, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        //post new movies
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

        //delete movie with id=2
        mockMvc.perform(delete("/api/movie?id=2"))
                .andExpect(status().is(200));

        //get all movies
        String actual = mockMvc
                .perform(get("/api/movie/all"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        movie.setId(1L);
        movie3.setId(3L);

        String expected = asJsonString(Arrays.asList(movie, movie3));

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