package cognitivity.exceptions;


/**
 * Created by ophir on 25/05/18.
 */
public class JsonTestParseError extends LoaderException {

    public static final String errorMessage = "Failed parsing test content";

    public JsonTestParseError(String jsonData) {
        super(jsonData);
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
