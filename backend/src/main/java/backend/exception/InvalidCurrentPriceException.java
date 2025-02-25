package backend.exception;

public class InvalidCurrentPriceException extends InvalidArgumentException {

    public InvalidCurrentPriceException(String message) {
        super(message);
    }

    public InvalidCurrentPriceException(String message, Throwable cause) {
        super(message, cause);
    }

}
