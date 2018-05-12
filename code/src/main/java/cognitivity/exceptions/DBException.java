package cognitivity.exceptions;

/**
 * An exception class for the DB exception.
 * This exception class was created to organise the DB exception in a neat way for the front end to handle.
 *
 * @author - Pe'er
 * @date - 2.2.18
 */

public class DBException extends CognitivityException {
    ErrorType type;
    Long id;

    public DBException(ErrorType type, Long id) {
        super("DBException : ");
        this.type = type;
        this.id = id;
    }

    public ErrorType getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "of questionType : " + type.name() + " with id : " + id;
    }
}

