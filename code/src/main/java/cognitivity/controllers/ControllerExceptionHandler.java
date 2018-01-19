package cognitivity.controllers;

import cognitivity.Exceptions.DBException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DBException.class)
    public ResponseEntity handleException(DBException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Trololo! It's working!!! Horray!");
    }
}
