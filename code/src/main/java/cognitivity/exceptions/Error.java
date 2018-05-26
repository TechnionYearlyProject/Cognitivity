package cognitivity.exceptions;

import com.sun.xml.internal.ws.developer.Serialization;

/**
 * Created by peers on 25/05/2018.
 * A class to send the Errors to the front end.
 * The class is build in a modular fashion to help parse the data correctly and efficiently in the client side.
 */
// @Serialization
public class Error {
    private ErrorClass errorClass;
    private String message;
    private ErrorType type;

    public Error(ErrorClass errorClass, String message, ErrorType type) {
        this.errorClass = errorClass;
        this.message = message;
        this.type = type;
    }
}
