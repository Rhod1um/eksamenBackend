package com.example.eksamentemplate.exception;


public class ResourceNotFoundException extends RuntimeException { //nu er det unchecked - runtime
    //skal have denne linje ved fejlmeddenlse på databaseadgang:
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
