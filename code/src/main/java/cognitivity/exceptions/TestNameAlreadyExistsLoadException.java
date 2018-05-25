package cognitivity.exceptions;


/**
 * Created by ophir on 25/05/18.
 */
public class TestNameAlreadyExistsLoadException extends LoaderException {
    public static final String errorMessage = "Cannot add test with this name, since a test with this name already exists in the DB.";

    public TestNameAlreadyExistsLoadException(String jsonData) {
        super(jsonData);
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
