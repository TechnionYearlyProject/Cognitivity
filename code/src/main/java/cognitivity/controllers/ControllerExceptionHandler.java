package cognitivity.controllers;

import cognitivity.exceptions.DBException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is designed to handle all the errors that may rise to the rest of the controllers.
 */
@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

    /**
     * Exception handler for DB errors.
     * This method is called when an error in DB accours.
     * Namely, Errors in adding, deleting and updating data in the DB.
     * @param e - The caught exception.
     * @return - A string containing information about the error.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DBException.class)
    public String handleDBException(DBException e){
        return "DB_ERR: "+e.getType().toString();
    }

    /**
     * Exception handler for the rest of the runtime exceptions.
     * Whenever an error that is not caught by the rest of the handlers accours,
     * this method catches them and sends the relevant data to the front end.
     * @param e - The caught exception.
     * @return - A string containing information about the error.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(DBException e){
        return "Runtime_ERR: "+e.getMessage();
    }
}