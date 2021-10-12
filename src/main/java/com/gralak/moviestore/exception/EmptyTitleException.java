package com.gralak.moviestore.exception;

public class EmptyTitleException extends RuntimeException
{
    public EmptyTitleException()
    {
        super("Column title is either null or empty string");
    }
}
