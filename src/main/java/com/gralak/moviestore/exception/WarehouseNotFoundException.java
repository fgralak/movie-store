package com.gralak.moviestore.exception;

public class WarehouseNotFoundException extends RuntimeException
{
    public WarehouseNotFoundException(Long id)
    {
        super("Could not find warehouse with given id: " + id);
    }
}
