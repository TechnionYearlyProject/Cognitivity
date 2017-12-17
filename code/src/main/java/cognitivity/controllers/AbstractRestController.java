package cognitivity.controllers;

import cognitivity.dao.AbstractEntity;
import cognitivity.repositories.AbstractRepository;
import cognitivity.services.AbstractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by ophir on 17/12/17.
 */
public class AbstractRestController<ServiceType extends AbstractService<? extends AbstractRepository<? extends AbstractEntity>>> {
    protected final ServiceType service;

    public AbstractRestController(ServiceType service) {
        this.service = service;
    }


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
