package cognitivity.exceptions;

/**
 * Created by ophir on 12/05/18.
 */
public class LoaderException extends CognitivityException {

    private final String fileName;

    public LoaderException(String fileName) {
        super("LoaderException : ");
        this.fileName = fileName;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Error reading from path : " + fileName;
    }
}
