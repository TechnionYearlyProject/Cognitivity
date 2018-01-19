package cognitivity.controllers;

import cognitivity.exceptions.DBException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This controller
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DBException.class)
    public String handleDBException(DBException e){
        return "DB_ERR: "+e.getType().toString();
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(DBException e){
        return "Runtime_ERR: "+e.getMessage();
    }
}
