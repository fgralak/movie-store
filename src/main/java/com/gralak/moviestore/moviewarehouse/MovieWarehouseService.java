package com.gralak.moviestore.moviewarehouse;

import com.gralak.moviestore.exception.MovieWarehouseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieWarehouseService
{
    private final MovieWarehouseRepo movieWarehouseRepo;

    public List<MovieWarehouse> getAllMovieWarehouses()
    {
        return movieWarehouseRepo.findAll();
    }

    public Map<Long, Integer> getAllWarehousesFromMovieId(Long movieId)
    {
        Set<MovieWarehouse> warehouses = movieWarehouseRepo.findAllWarehousesFromMovieId(movieId);
        Map<Long, Integer> map = new HashMap<>();
        for (MovieWarehouse warehouse : warehouses)
        {
            map.put(warehouse.getWarehouse().getId(), warehouse.getQuantity());
        }
        return map;
    }

    public Map<Long, Integer> getAllMoviesFromWarehouseId(Long warehouseId)
    {
        Set<MovieWarehouse> movies = movieWarehouseRepo.findAllMoviesFromWarehouseId(warehouseId);
        Map<Long, Integer> map = new HashMap<>();
        for (MovieWarehouse movie : movies)
        {
            map.put(movie.getMovie().getId(), movie.getQuantity());
        }
        return map;
    }

    public MovieWarehouse getMovieWarehouseById(MovieWarehouseId id)
    {
        return movieWarehouseRepo.findById(id).orElseThrow(() -> new MovieWarehouseNotFoundException(id));
    }

    public MovieWarehouse addMovieWarehouse(MovieWarehouse movieWarehouse)
    {
        return movieWarehouseRepo.save(movieWarehouse);
    }

    public MovieWarehouse updateMovieWarehouse(MovieWarehouse movieWarehouse)
    {
        boolean exists = movieWarehouseRepo.existsById(movieWarehouse.getId());
        if (!exists)
        {
            throw new MovieWarehouseNotFoundException(movieWarehouse.getId());
        }

        return movieWarehouseRepo.save(movieWarehouse);
    }

    public void deleteMovieWarehouseById(MovieWarehouseId id)
    {
        boolean exists = movieWarehouseRepo.existsById(id);
        if (!exists)
        {
            throw new MovieWarehouseNotFoundException(id);
        }

        movieWarehouseRepo.deleteById(id);
    }
}
