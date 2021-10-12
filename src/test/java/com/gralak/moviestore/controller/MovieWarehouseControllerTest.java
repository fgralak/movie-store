package com.gralak.moviestore.controller;

import com.gralak.moviestore.movie.Movie;
import com.gralak.moviestore.moviewarehouse.MovieWarehouse;
import com.gralak.moviestore.moviewarehouse.MovieWarehouseId;
import com.gralak.moviestore.warehouse.Warehouse;
import com.gralak.moviestore.moviewarehouse.MovieWarehouseController;
import com.gralak.moviestore.moviewarehouse.MovieWarehouseService;
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
class MovieWarehouseControllerTest
{
    @Mock
    private MovieWarehouseService movieWarehouseService;

    private MovieWarehouseController testedController;

    @BeforeEach
    void setUp()
    {
        testedController = new MovieWarehouseController(movieWarehouseService);
    }

    @Test
    void shouldGetAllMovieWarehouses()
    {
        //given
        //when
        testedController.getAllMovieWarehouses();

        //then
        verify(movieWarehouseService).getAllMovieWarehouses();
    }

    @Test
    void shouldGetMovieWarehouseById()
    {
        //given
        Long movieId = 1L;
        Long warehouseId = 1L;


        //when
        testedController.getMovieWarehouseById(movieId, warehouseId);

        //then
        verify(movieWarehouseService).getMovieWarehouseById(new MovieWarehouseId(movieId, warehouseId));
    }

    @Test
    void shouldGetAllMoviesFromWarehouseId()
    {
        //given
        Long warehouseId = 1L;

        //when
        testedController.getAllMoviesFromWarehouseId(warehouseId);

        //then
        verify(movieWarehouseService).getAllMoviesFromWarehouseId(warehouseId);
    }

    @Test
    void shouldGetAllWarehousesFromMovieId()
    {
        //given
        Long movieId = 1L;

        //when
        testedController.getAllWarehousesFromMovieId(movieId);

        //then
        verify(movieWarehouseService).getAllWarehousesFromMovieId(movieId);
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
        testedController.addMovieWarehouse(movieWarehouse);

        //then
        ArgumentCaptor<MovieWarehouse> captor = ArgumentCaptor.forClass(MovieWarehouse.class);

        verify(movieWarehouseService).addMovieWarehouse(captor.capture());

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

        //when
        testedController.updateMovieWarehouse(movieWarehouse);

        //then
        ArgumentCaptor<MovieWarehouse> captor = ArgumentCaptor.forClass(MovieWarehouse.class);

        verify(movieWarehouseService).updateMovieWarehouse(captor.capture());

        assertThat(captor.getValue()).isEqualTo(movieWarehouse);
    }

    @Test
    void shouldDeleteMovieWarehouseById()
    {
        //given
        Long movieId = 1L;
        Long warehouseId = 1L;

        //when
        testedController.deleteMovieWarehouseById(movieId, warehouseId);

        //then
        ArgumentCaptor<MovieWarehouseId> captor = ArgumentCaptor.forClass(MovieWarehouseId.class);

        verify(movieWarehouseService).deleteMovieWarehouseById(captor.capture());

        assertThat(captor.getValue()).isEqualTo(new MovieWarehouseId(movieId, warehouseId));
    }
}