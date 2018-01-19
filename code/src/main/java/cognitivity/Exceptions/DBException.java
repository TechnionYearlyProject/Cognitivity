package cognitivity.Exceptions;

public class DBException extends Exception {
    ErrorType type;
    public DBException(ErrorType type) {
        super();
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}

