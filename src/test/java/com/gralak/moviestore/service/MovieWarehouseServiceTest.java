package com.gralak.moviestore.service;

import com.gralak.moviestore.movie.Movie;
import com.gralak.moviestore.moviewarehouse.MovieWarehouse;
import com.gralak.moviestore.moviewarehouse.MovieWarehouseId;
import com.gralak.moviestore.moviewarehouse.MovieWarehouseService;
import com.gralak.moviestore.warehouse.Warehouse;
import com.gralak.moviestore.exception.MovieWarehouseNotFoundException;
import com.gralak.moviestore.moviewarehouse.MovieWarehouseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieWarehouseServiceTest
{
    @Mock
    private MovieWarehouseRepo movieWarehouseRepo;

    private MovieWarehouseService testedService;

    @BeforeEach
    void setUp()
    {
        testedService = new MovieWarehouseService(movieWarehouseRepo);
    }

    @Test
    void shouldGetAllMovieWarehouses()
    {
        //given
        //when
        testedService.getAllMovieWarehouses();

        //then
        verify(movieWarehouseRepo).findAll();
    }

    @Test
    void getAllWarehousesFromMovieId()
    {
        //given
        Long movieId = 1L;
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        movie.setId(movieId);
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        Warehouse warehouse2 = new Warehouse("Wielkopolska 19", "Poznań", "00-210");
        warehouse.setId(1L);
        warehouse2.setId(2L);
        Set<MovieWarehouse> warehouses = new HashSet<>();
        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);
        MovieWarehouse movieWarehouse2 = new MovieWarehouse(movie, warehouse2, 20);
        warehouses.add(movieWarehouse);
        warehouses.add(movieWarehouse2);

        Map<Long, Integer> expected = new HashMap<>();
        expected.put(1L, 10);
        expected.put(2L, 20);

        given(movieWarehouseRepo.findAllWarehousesFromMovieId(movieId)).willReturn(warehouses);

        //when
        Map<Long, Integer> actual = testedService.getAllWarehousesFromMovieId(movieId);

        //then
        verify(movieWarehouseRepo).findAllWarehousesFromMovieId(movieId);

        assertEquals(expected, actual);
    }

    @Test
    void getAllMoviesFromWarehouseId()
    {
        //given
        Long warehouseId = 1L;
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        Movie movie2 = new Movie("The Lord Of The Rings II", 2002, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        movie.setId(1L);
        movie2.setId(2L);

        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        warehouse.setId(warehouseId);

        Set<MovieWarehouse> movies = new HashSet<>();
        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);
        MovieWarehouse movieWarehouse2 = new MovieWarehouse(movie2, warehouse, 20);
        movies.add(movieWarehouse);
        movies.add(movieWarehouse2);

        Map<Long, Integer> expected = new HashMap<>();
        expected.put(1L, 10);
        expected.put(2L, 20);

        given(movieWarehouseRepo.findAllMoviesFromWarehouseId(warehouseId)).willReturn(movies);

        //when
        Map<Long, Integer> actual = testedService.getAllMoviesFromWarehouseId(warehouseId);

        //then
        verify(movieWarehouseRepo).findAllMoviesFromWarehouseId(warehouseId);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetMovieWarehouseById()
    {
        //given
        MovieWarehouseId id = new MovieWarehouseId(1L, 1L);
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);

        given(movieWarehouseRepo.findById(id)).willReturn(java.util.Optional.of(movieWarehouse));

        //when
        testedService.getMovieWarehouseById(id);

        //then
        verify(movieWarehouseRepo).findById(id);
    }

    @Test
    void shouldThrowMovieWarehouseNotFoundException()
    {
        //given
        Long id = 1L;
        MovieWarehouseId mvId = new MovieWarehouseId(id, id);
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        movie.setId(id);
        warehouse.setId(id);
        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);

        //when
        //then
        assertThatThrownBy(() -> testedService.getMovieWarehouseById(mvId))
                .isInstanceOf(MovieWarehouseNotFoundException.class)
                .hasMessage("Could not find movie-warehouse relation with given id: " + mvId);

        assertThatThrownBy(() -> testedService.updateMovieWarehouse(movieWarehouse))
                .isInstanceOf(MovieWarehouseNotFoundException.class)
                .hasMessage("Could not find movie-warehouse relation with given id: " + movieWarehouse.getId());

        assertThatThrownBy(() -> testedService.deleteMovieWarehouseById(mvId))
                .isInstanceOf(MovieWarehouseNotFoundException.class)
                .hasMessage("Could not find movie-warehouse relation with given id: " + mvId);
    }

    @Test
    void shouldAddMovieWarehouse()
    {
        //given
        Long id = 1L;
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        movie.setId(id);
        warehouse.setId(id);
        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);

        //when
        testedService.addMovieWarehouse(movieWarehouse);

        //then
        ArgumentCaptor<MovieWarehouse> captor = ArgumentCaptor.forClass(MovieWarehouse.class);

        verify(movieWarehouseRepo).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(movieWarehouse);
    }

    @Test
    void shouldUpdateMovieWarehouse()
    {
        //given
        Long id = 1L;
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        movie.setId(id);
        warehouse.setId(id);
        MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);

        given(movieWarehouseRepo.existsById(movieWarehouse.getId())).willReturn(true);

        //when
        testedService.updateMovieWarehouse(movieWarehouse);

        //then
        ArgumentCaptor<MovieWarehouse> captor = ArgumentCaptor.forClass(MovieWarehouse.class);

        verify(movieWarehouseRepo).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(movieWarehouse);
    }

    @Test
    void shouldDeleteMovieWarehouseById()
    {
        //given
        MovieWarehouseId id = new MovieWarehouseId(1L, 1L);

        given(movieWarehouseRepo.existsById(id)).willReturn(true);

        //when
        testedService.deleteMovieWarehouseById(id);

        //then
        ArgumentCaptor<MovieWarehouseId> captor = ArgumentCaptor.forClass(MovieWarehouseId.class);

        verify(movieWarehouseRepo).deleteById(captor.capture());

        assertThat(captor.getValue()).isEqualTo(id);
    }
}