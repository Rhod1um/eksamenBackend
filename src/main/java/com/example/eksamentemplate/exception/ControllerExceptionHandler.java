package com.example.eksamentemplate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice //for at controllerklasserne kan bruge denne.
//når spring starter laver den instans af denne klasse
public class ControllerExceptionHandler {

    //når en ResourceNotFoundException throwes et sted i koden kommer vi her ind
    @ExceptionHandler(ResourceNotFoundException.class) //bruger vores egen!
    ResponseEntity<ErrorMessage> resourceNotFoundException(RuntimeException ex){
        ErrorMessage message = new ErrorMessage( //laver instans af vores egen ErrorMessage klasse
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage()
        );
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    //hver endpoint kan smide en exception så her kan vi specificere hvad der skal ske ved en exception
    //vi gider ikke skrive exception handler kode i service, derfor her

    // exception uden brug af egen exception. Returnerer bare en string
    @ExceptionHandler(ObjectAlreadyExistsException.class)
    ResponseEntity<ErrorMessage> ObjectAlreadyExistsException(RuntimeException ex) {
        ErrorMessage message = new ErrorMessage( //laver instans af vores egen ErrorMessage klasse
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage()
        );
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }
    /*
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    ResponseEntity<String> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return new ResponseEntity<>(bodyOfResponse + "\n" + ex.getMessage(), HttpStatus.CONFLICT);
    }*/
}
