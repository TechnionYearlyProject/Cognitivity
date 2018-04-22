package cognitivity.exceptions;

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

