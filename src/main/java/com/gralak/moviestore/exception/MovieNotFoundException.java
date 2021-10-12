package com.gralak.moviestore.exception;

public class MovieNotFoundException extends RuntimeException
{
    public MovieNotFoundException(Long id)
    {
        super("Could not find movie with given id: " + id);
    }
}
