package com.gralak.moviestore.exception;

import com.gralak.moviestore.moviewarehouse.MovieWarehouseId;

public class MovieWarehouseNotFoundException extends RuntimeException
{
    public MovieWarehouseNotFoundException(MovieWarehouseId id)
    {
        super("Could not find movie-warehouse relation with given id: " + id);
    }
}
