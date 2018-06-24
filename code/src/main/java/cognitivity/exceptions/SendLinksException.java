package cognitivity.exceptions;

/**
 * Created by ophir on 26/5/18.
 */
public class SendLinksException extends CognitivityException {

    private final ErrorType type;

    public enum ErrorType {
        NOT_REGISTERED,
        MESSAGE_FAILED_TO_SEND,
        EMPTY_EMAILS
    }

    public SendLinksException(ErrorType type) {
        super("");
        this.type = type;
    }

    @Override
    public String getMessage() {
        String msg = "";
        switch (type) {
            case NOT_REGISTERED:
                msg = "Failed to send link to some of the emails";
                break;
            case MESSAGE_FAILED_TO_SEND:
                msg = "Failed to send link to some of the emails";
                break;
            case EMPTY_EMAILS:
                msg = "No emails were supplied";
                break;
        }

        return msg;
    }
}
