package com.gralak.moviestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ExceptionAdvice
{
    @ExceptionHandler(EmptyTitleException.class)
    public ResponseEntity<Exception> emptyTitleHandler(EmptyTitleException e)
    {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Exception exception = new Exception(e.getMessage(), httpStatus, ZonedDateTime.now());
        return new ResponseEntity<>(exception, httpStatus);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Exception> movieNotFoundHandler(MovieNotFoundException e)
    {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Exception exception = new Exception(e.getMessage(), httpStatus, ZonedDateTime.now());
        return new ResponseEntity<>(exception, httpStatus);
    }

    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<Exception> warehouseNotFoundHandler(WarehouseNotFoundException e)
    {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Exception exception = new Exception(e.getMessage(), httpStatus, ZonedDateTime.now());
        return new ResponseEntity<>(exception, httpStatus);
    }

    @ExceptionHandler(MissingWarehouseDataException.class)
    public ResponseEntity<Exception> missingWarehouseDataHandler(MissingWarehouseDataException e)
    {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Exception exception = new Exception(e.getMessage(), httpStatus, ZonedDateTime.now());
        return new ResponseEntity<>(exception, httpStatus);
    }

    @ExceptionHandler(MovieWarehouseNotFoundException.class)
    public ResponseEntity<Exception> movieWarehouseNotFoundHandler(MovieWarehouseNotFoundException e)
    {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Exception exception = new Exception(e.getMessage(), httpStatus, ZonedDateTime.now());
        return new ResponseEntity<>(exception, httpStatus);
    }
}
