package com.gralak.moviestore.moviewarehouse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie-warehouse")
@Api(tags = {"Movie-Warehouse Controller"})
public class MovieWarehouseController
{
    private final MovieWarehouseService movieWarehouseService;

    @ApiOperation(value = "Get all available movie-warehouse relations", notes = "Retrieve a list of all movie-warehouse relations")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "List of relations retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get all relations"),
                    @ApiResponse(code = 404, message = "Relations couldn't be found")}
    )
    @GetMapping("/all")
    public ResponseEntity<List<MovieWarehouse>> getAllMovieWarehouses()
    {
        List<MovieWarehouse> all = movieWarehouseService.getAllMovieWarehouses();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get movie-warehouse relation with a given id",
            notes = "Retrieve a movie-warehouse relation with a given id",
            response = MovieWarehouse.class)
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Relation with given id retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get a relation"),
                    @ApiResponse(code = 404, message = "Relation with a given id couldn't be found")}
    )
    @GetMapping
    public ResponseEntity<MovieWarehouse> getMovieWarehouseById(@RequestParam Long movieId, @RequestParam Long warehouseId)
    {
        MovieWarehouse movieWarehouse = movieWarehouseService.getMovieWarehouseById(new MovieWarehouseId(movieId, warehouseId));
        return new ResponseEntity<>(movieWarehouse, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get all available movies from a particular warehouseId",
            notes = "Retrieve a list of movies from a particular warehouseId")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "List of movies retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get the list"),
                    @ApiResponse(code = 404, message = "List of movies couldn't be found")}
    )
    @GetMapping("/all-movies")
    public ResponseEntity<Map<Long, Integer>> getAllMoviesFromWarehouseId(@RequestParam Long warehouseId)
    {
        Map<Long, Integer> movies = movieWarehouseService.getAllMoviesFromWarehouseId(warehouseId);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get all available warehouses from a particular movieId",
            notes = "Retrieve a list of all warehouses from a particular movieId")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "List of warehouses retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get the list"),
                    @ApiResponse(code = 404, message = "List of warehouses couldn't be found")}
    )
    @GetMapping("/all-warehouses")
    public ResponseEntity<Map<Long, Integer>> getAllWarehousesFromMovieId(@RequestParam Long movieId)
    {
        Map<Long, Integer> warehouses = movieWarehouseService.getAllWarehousesFromMovieId(movieId);
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Add a new movie-warehouse relation",
            notes = "Add a new movie-warehouse relation into the system",
            response = MovieWarehouse.class)
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "Relation created successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to create a relation"),
                    @ApiResponse(code = 404, message = "Relation couldn't be created")}
    )
    @PostMapping
    public ResponseEntity<MovieWarehouse> addMovieWarehouse(@RequestBody MovieWarehouse movieWarehouseToAdd)
    {
        MovieWarehouse movieWarehouse = movieWarehouseService.addMovieWarehouse(movieWarehouseToAdd);
        return new ResponseEntity<>(movieWarehouse, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Update existing movie-warehouse relation",
            notes = "Update existing information about movie-warehouse relation ",
            response = MovieWarehouse.class)
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Relation information updated successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to update a relation"),
                    @ApiResponse(code = 404, message = "Relation couldn't be updated")}
    )
    @PutMapping
    public ResponseEntity<MovieWarehouse> updateMovieWarehouse(@RequestBody MovieWarehouse movieWarehouseForUpdate)
    {
        MovieWarehouse movieWarehouse = movieWarehouseService.updateMovieWarehouse(movieWarehouseForUpdate);
        return new ResponseEntity<>(movieWarehouse, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Delete movie-warehouse relation with given id",
            notes = "Delete existing movie-warehouse relation with given id")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Relation information deleted successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to delete a relation"),
                    @ApiResponse(code = 404, message = "Relation couldn't be deleted")}
    )
    @DeleteMapping
    public ResponseEntity<?> deleteMovieWarehouseById(@RequestParam Long movieId, @RequestParam Long warehouseId)
    {
        movieWarehouseService.deleteMovieWarehouseById(new MovieWarehouseId(movieId, warehouseId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
