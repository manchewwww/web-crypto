package backend.exception;

public class NotEnoughCryptoQuantityException extends InvalidArgumentException {

    public NotEnoughCryptoQuantityException(String message) {
        super(message);
    }

    public NotEnoughCryptoQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

}
