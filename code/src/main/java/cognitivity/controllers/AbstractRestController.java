package cognitivity.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;



/**
 * Created by ophir on 17/12/17.
 */
public abstract class AbstractRestController<ServiceType> {

    protected final ServiceType service;

    public AbstractRestController(ServiceType service) { this.service = service; }


    /**
     * Error handler for backend errors - a 400 status code will be sent back, and the body
     * of the message contains the exception text.
     *
     * @param exc - the exception caught
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
