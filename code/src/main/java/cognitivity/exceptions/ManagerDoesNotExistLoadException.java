package cognitivity.exceptions;

/**
 * Created by ophir on 25/05/18.
 */
public class ManagerDoesNotExistLoadException extends LoaderException {

    public static final String errorMessage = "Manager is not in the DB";

    public ManagerDoesNotExistLoadException(String jsonData) {
        super(jsonData);
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
