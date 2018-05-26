package cognitivity.exceptions;

/**
 * Created by ophir on 26/5/18.
 */
public class SendLinksException extends Throwable {
    @Override
    public String getMessage() {
        return "Some subjects were not registered";
    }
}
