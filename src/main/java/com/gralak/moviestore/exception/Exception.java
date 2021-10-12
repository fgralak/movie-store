package com.gralak.moviestore.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class Exception
{
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}
