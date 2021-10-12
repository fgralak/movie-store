package com.gralak.moviestore.service;

import com.gralak.moviestore.movie.Movie;
import com.gralak.moviestore.exception.EmptyTitleException;
import com.gralak.moviestore.exception.MovieNotFoundException;
import com.gralak.moviestore.movie.MovieRepo;
import com.gralak.moviestore.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest
{
    @Mock
    private MovieRepo movieRepo;

    private MovieService testedService;

    @BeforeEach
    void setUp()
    {
        testedService = new MovieService(movieRepo);
    }

    @Test
    void shouldGetAllMovies()
    {
        //when
        testedService.getMovies();

        //then
        verify(movieRepo).findAll();
    }

    @Test
    void shouldGetMovieById()
    {
        //given
        Long id = 1L;
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        given(movieRepo.findById(id)).willReturn(java.util.Optional.of(movie));

        //when
        testedService.getMovieById(id);

        //then
        verify(movieRepo).findById(id);
    }

    @Test
    void shouldThrowMovieNotFoundException()
    {
        //given
        Long id = 1L;
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        movie.setId(id);

        //when
        //then
        assertThatThrownBy(() -> testedService.getMovieById(id))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessage("Could not find movie with given id: " + id);

        assertThatThrownBy(() -> testedService.updateMovie(movie))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessage("Could not find movie with given id: " + id);

        assertThatThrownBy(() -> testedService.deleteMovieById(id))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessage("Could not find movie with given id: " + id);
    }

    @Test
    void shouldAddMovie()
    {
        //given
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        //when
        testedService.addMovie(movie);

        //then
        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);

        verify(movieRepo).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(movie);
    }

    @Test
    void shouldUpdateMovie()
    {
        //given
        Long id = 1L;
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        movie.setId(id);

        given(movieRepo.existsById(id)).willReturn(true);

        //when
        testedService.updateMovie(movie);

        //then
        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);

        verify(movieRepo).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(movie);
    }

    @Test
    void shouldThrowEmptyTitleException()
    {
        //given
        Long id = 1L;
        Movie movie = new Movie("", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        movie.setId(id);

        given(movieRepo.existsById(id)).willReturn(true);

        //when
        //then
        assertThatThrownBy(() -> testedService.addMovie(movie))
                .isInstanceOf(EmptyTitleException.class)
                .hasMessage("Column title is either null or empty string");

        assertThatThrownBy(() -> testedService.updateMovie(movie))
                .isInstanceOf(EmptyTitleException.class)
                .hasMessage("Column title is either null or empty string");
    }

    @Test
    void deleteMovieById()
    {
        //given
        Long id = 1L;

        given(movieRepo.existsById(id)).willReturn(true);

        //when
        testedService.deleteMovieById(id);

        //then
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        verify(movieRepo).deleteById(captor.capture());

        assertThat(captor.getValue()).isEqualTo(id);
    }
}