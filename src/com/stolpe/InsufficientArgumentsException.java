package com.stolpe;

public class InsufficientArgumentsException extends RuntimeException {
    public InsufficientArgumentsException() { }

    public InsufficientArgumentsException(String s ) {
        super( s );
    }
}
