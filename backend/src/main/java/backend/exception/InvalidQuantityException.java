package backend.exception;

public class InvalidQuantityException extends InvalidArgumentException {

    public InvalidQuantityException(String message) {
        super(message);
    }

    public InvalidQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

}
