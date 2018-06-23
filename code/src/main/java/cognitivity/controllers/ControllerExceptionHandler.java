package cognitivity.controllers;

import cognitivity.exceptions.*;
import cognitivity.exceptions.Error;
import cognitivity.web.app.CognitivityApplicationInsights;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is designed to handle all the errors that may rise to the rest of the controllers.
 *
 * @author - Pe'er
 * @Date - 2.2.18
 */
@ControllerAdvice
@RestController
@RequestMapping(
        produces = "application/json;charset=UTF-8"
)
public class ControllerExceptionHandler {

    /**
     * Exception handler for DB errors.
     * This method is called when an error in DB accours.
     * Namely, Errors in adding, deleting and updating data in the DB.
     *
     * @param e - The caught exception.
     * @return - An object containing the information about the error that is required to the front end.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DBException.class)
    public ResponseEntity<Error> handleDBException(DBException e) {
        CognitivityApplicationInsights.getInstance().trackFailure(e);
        Error error = new Error(ErrorClass.DB, e.getMessage(), e.getType());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler for Empty result data access exceptions.
     * This method is called when an empty result exception accours.
     * Namely, when an access to the the DB brings no result, this exception is thrown.
     * As a convention in the project, whenever an access to the DB brings no result, null should be returned.
     *
     * @param e - The caught exception.
     * @return - An object containing the information about the error that is required to the front end.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Error> handleEmptyResultException(EmptyResultDataAccessException e) {
        CognitivityApplicationInsights.getInstance().trackFailure(e);
        Error error = new Error(ErrorClass.EMPTY_RESULT, "The query in the database has given no results", null);
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }


    /**
     * Exception handler for exceptions in test loader.
     * Whenever an error in the test loader accours,
     * this method catches them and sends the relevant data to the front end.
     *
     * @param e - The caught exception.
     * @return - An object containing the information about the error that is required to the front end.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(LoaderException.class)
    public ResponseEntity<Error> handleLoaderException(LoaderException e) {
        CognitivityApplicationInsights.getInstance().trackFailure(e);
        Error error = new Error(ErrorClass.LOAD, "There has been a load error in the system. Error was: " + e.getMessage() +
                "\nFor more information please refer to the log.", null);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler for exceptions in test loader.
     * Whenever an error in the test loader accours,
     * this method catches them and sends the relevant data to the front end.
     *
     * @param e - The caught exception.
     * @return - An object containing the information about the error that is required to the front end.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SendLinksException.class)
    public ResponseEntity<Error> handleSendLinksException(SendLinksException e) {
        Error error = new Error(ErrorClass.SEND_LINKS,
                "There was an error when attempting to send links to subjects." +
                        " Error was: " + e.getMessage() +
                        "\nFor more information please refer to the log.", null);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Exception handler for the rest of the runtime exceptions.
     * Whenever an error that is not caught by the rest of the handlers accours,
     * this method catches them and sends the relevant data to the front end.
     *
     * @param e - The caught exception.
     * @return - An object containing the information about the error that is required to the front end.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> handleRuntimeException(RuntimeException e) {
        CognitivityApplicationInsights.getInstance().trackFailure(e);
        Error error = new Error(ErrorClass.RUNTIME, "There has been a runtime error in the system. Error was: " + e.getMessage() +
                "\nType is:" + e.getClass().getName() + "\nFor more information please refer to the log.", null);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler for the rest of the runtime exceptions.
     * Whenever an error that is not caught by the rest of the handlers accours,
     * this method catches them and sends the relevant data to the front end.
     *
     * @param e - The caught exception.
     * @return - An object containing the information about the error that is required to the front end.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleAnyException(Exception e) {
        CognitivityApplicationInsights.getInstance().trackFailure(e);
        Error error = new Error(ErrorClass.RUNTIME, "There has been an error in the system. Error was: " + e.getMessage() +
                "\nType is:" + e.getClass().getName() + "\nFor more information please refer to the log.", null);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
