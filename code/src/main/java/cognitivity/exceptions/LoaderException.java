package cognitivity.exceptions;

/**
 * Created by ophir on 12/05/18.
 */
public class LoaderException extends CognitivityException {

    private final String jsonData;
    private String message;

    public LoaderException(String jsonData) {
        super("LoaderException : ");
        this.jsonData = jsonData;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Error reading from file.\n" +
                "Message : " + message + "\n" +
                "Data was : " + jsonData;
    }
}
