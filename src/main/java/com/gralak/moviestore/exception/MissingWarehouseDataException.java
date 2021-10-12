package com.gralak.moviestore.exception;

public class MissingWarehouseDataException extends RuntimeException
{
    public MissingWarehouseDataException()
    {
        super("Empty value in column: streetAndNumber, city or/and postcode");
    }
}
