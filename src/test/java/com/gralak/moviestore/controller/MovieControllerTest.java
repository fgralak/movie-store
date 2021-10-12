package com.gralak.moviestore.controller;

import com.gralak.moviestore.movie.Movie;
import com.gralak.moviestore.movie.MovieController;
import com.gralak.moviestore.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest
{
    @Mock
    private MovieService movieService;

    private MovieController testedController;

    @BeforeEach
    void setUp()
    {
        testedController = new MovieController(movieService);
    }

    @Test
    void shouldGetAllMovies()
    {
        //given
        //when
        testedController.getAllMovies();

        //then
        verify(movieService).getMovies();
    }

    @Test
    void shouldGetMovieById()
    {
        //given
        Long movieId = 1L;

        //when
        testedController.getMovieById(movieId);

        //then
        verify(movieService).getMovieById(movieId);
    }

    @Test
    void shouldAddMovie()
    {
        //given
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        //when
        testedController.addMovie(movie);

        //then
        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);

        verify(movieService).addMovie(captor.capture());

        assertThat(captor.getValue()).isEqualTo(movie);
    }

    @Test
    void shouldUpdateMovie()
    {
        //given
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        //when
        testedController.updateMovie(movie);

        //then
        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);

        verify(movieService).updateMovie(captor.capture());

        assertThat(captor.getValue()).isEqualTo(movie);
    }

    @Test
    void shouldDeleteMovieById()
    {
        //given
        Long movieId = 1L;

        //when
        testedController.deleteMovieById(movieId);

        //then
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        verify(movieService).deleteMovieById(captor.capture());

        assertThat(captor.getValue()).isEqualTo(movieId);
    }
}