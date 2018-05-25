package cognitivity.controllers;


import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import cognitivity.exceptions.LoaderException;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * Created by ophir on 25/5/17.
 */
public class ExceptionCoverageTest {
    @Test
    public void testExceptionCoverage() {
        ControllerExceptionHandler handler = new ControllerExceptionHandler();
        handler.handleDBException(new DBException(ErrorType.GET, 1L));
        handler.handleEmptyResultException(new EmptyResultDataAccessException(44));
        handler.handleLoaderException(new LoaderException("data"));
        handler.handleRuntimeException(new RuntimeException());
    }
}
