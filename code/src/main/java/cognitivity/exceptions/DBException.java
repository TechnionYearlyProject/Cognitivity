package cognitivity.exceptions;

/**
 * An exception class for the DB exception.
 * This exception class was created to organise the DB exception in a neat way for the front end to handle.
 * @author - Pe'er
 * @date - 2.2.18
 */

public class DBException extends Exception {
    ErrorType type;
    long id;

    public DBException(ErrorType type, long id) {
        super();
        this.type = type;
        this.id = id;
    }

    public ErrorType getType() {
        return type;
    }
}

