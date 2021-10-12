package com.gralak.moviestore.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
@Api(tags = {"Movie Controller"})
public class MovieController
{
    private final MovieService movieService;

    @ApiOperation(value = "Get all available movies", notes = "Retrieve a list of all movies")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "List of movies retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get all movies"),
                    @ApiResponse(code = 404, message = "Movies couldn't be found")}
    )
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies()
    {
        List<Movie> movies = movieService.getMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @ApiOperation(value = "Get movie with a given id", notes = "Retrieve a movie with a given id", response = Movie.class)
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Movie with given id retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get a movie"),
                    @ApiResponse(code = 404, message = "Movie with a given id couldn't be found")}
    )
    @GetMapping
    public ResponseEntity<Movie> getMovieById(@RequestParam Long id)
    {
        Movie movie = movieService.getMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @ApiOperation(value = "Add a new movie", notes = "Add a new movie information into the system", response = Movie.class)
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "Movie created successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to create a movie"),
                    @ApiResponse(code = 404, message = "Movie couldn't be created")}
    )
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movieToAdd)
    {
        Movie movie = movieService.addMovie(movieToAdd);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update existing movie", notes = "Update existing movie information", response = Movie.class)
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Movie information updated successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to update a movie"),
                    @ApiResponse(code = 404, message = "Movie couldn't be updated")}
    )
    @PutMapping
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movieForUpdate)
    {
        Movie movie = movieService.updateMovie(movieForUpdate);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete movie with given id", notes = "Delete existing movie with given id")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Movie information deleted successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to delete a movie"),
                    @ApiResponse(code = 404, message = "Movie couldn't be deleted")}
    )
    @DeleteMapping
    public ResponseEntity<?> deleteMovieById(@RequestParam Long id)
    {
        movieService.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
