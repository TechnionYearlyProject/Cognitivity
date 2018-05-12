package cognitivity.exceptions;

/**
 * Created by ophir on 12/05/18.
 */
public class CognitivityException extends Exception {

    private String message;

    public CognitivityException(String message) {
        this.message = message;
    }

    public CognitivityException(Exception cause) {
        this.message = cause.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
