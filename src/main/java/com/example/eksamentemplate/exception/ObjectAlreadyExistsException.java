package com.example.eksamentemplate.exception;

public class ObjectAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
