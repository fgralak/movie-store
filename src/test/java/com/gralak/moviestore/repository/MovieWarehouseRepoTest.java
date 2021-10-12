package com.gralak.moviestore.repository;

import com.gralak.moviestore.movie.Movie;
import com.gralak.moviestore.movie.MovieRepo;
import com.gralak.moviestore.moviewarehouse.MovieWarehouse;
import com.gralak.moviestore.moviewarehouse.MovieWarehouseRepo;
import com.gralak.moviestore.warehouse.Warehouse;
import com.gralak.moviestore.warehouse.WarehouseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class MovieWarehouseRepoTest
{
    @Autowired
    private MovieWarehouseRepo testedRepo;

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private WarehouseRepo warehouseRepo;

    @BeforeEach
    void setUp()
    {
        testedRepo.deleteAll();
        movieRepo.deleteAll();
        warehouseRepo.deleteAll();
    }

    @Test
    void shouldFindAllMoviesFromWarehouseId()
    {
        //given
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
        Movie movie2 = new Movie("The Lord Of The Rings II", 2002, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        movieRepo.saveAll(Arrays.asList(movie, movie2));

        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        warehouseRepo.save(warehouse);

        MovieWarehouse mw = new MovieWarehouse(movie, warehouse, 10);
        MovieWarehouse mw2 = new MovieWarehouse(movie2, warehouse, 20);

        testedRepo.save(mw);
        testedRepo.save(mw2);

        Set<MovieWarehouse> expected = new HashSet<>(Arrays.asList(mw, mw2));

        //when
        Set<MovieWarehouse> actual = testedRepo.findAllMoviesFromWarehouseId(warehouse.getId());

        //then
        assertEquals(expected, actual);

    }

    @Test
    void shouldFindAllWarehousesFromMovieId()
    {
        //given
        Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
                Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

        movieRepo.save(movie);

        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        Warehouse warehouse2 = new Warehouse("Wielkopolska 19", "Poznań", "00-210");
        Warehouse warehouse3 = new Warehouse("Małopolska 19", "Kraków", "00-654");

        warehouseRepo.saveAll(Arrays.asList(warehouse, warehouse2, warehouse3));

        MovieWarehouse mw = new MovieWarehouse(movie, warehouse, 10);
        MovieWarehouse mw2 = new MovieWarehouse(movie, warehouse2, 20);
        MovieWarehouse mw3 = new MovieWarehouse(movie, warehouse3, 50);

        testedRepo.save(mw);
        testedRepo.save(mw2);
        testedRepo.save(mw3);

        Set<MovieWarehouse> expected = new HashSet<>(Arrays.asList(mw, mw2, mw3));

        //when
        Set<MovieWarehouse> actual = testedRepo.findAllWarehousesFromMovieId(movie.getId());

        //then
        assertEquals(expected, actual);
    }
}