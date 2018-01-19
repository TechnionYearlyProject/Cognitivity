package cognitivity.controllers;

import cognitivity.Exceptions.DBException;
import cognitivity.dto.TestWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(basePackages = "cognitivity.controllers")
public class ControllerExceptionHandler {

    @ExceptionHandler(DBException.class)
    public List<TestWrapper> handleException(DBException e){
        System.out.println("whattt2s");
        return new ArrayList<TestWrapper>();
//        return 1;
        //return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Trololo! It's working!!! Horray!");
    }
}
