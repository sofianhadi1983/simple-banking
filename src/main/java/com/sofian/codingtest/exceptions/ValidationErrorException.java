package com.sofian.codingtest.exceptions;

public class ValidationErrorException  extends RuntimeException {
    public ValidationErrorException() {
        super();
    }

    public ValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationErrorException(String message) {
        super(message);
    }

    public ValidationErrorException(Throwable cause) {
        super(cause);
    }
}
