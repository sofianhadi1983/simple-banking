package com.sofian.codingtest.exceptions;

public class DataAlreadyExistException extends RuntimeException {
    public DataAlreadyExistException() {
        super();
    }

    public DataAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAlreadyExistException(String message) {
        super(message);
    }

    public DataAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
