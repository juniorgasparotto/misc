package cardmanager.ciandt.com.cardmanager.exception;

/**
 * This class represents an client side error that could be thrown when interacting with an remote
 * repository.
 */
public class ClientRequestException extends ServerException {

    public int mHttpCode;

    /**
     * Builds a new ClientRequestException object.
     *
     * @param httpCode The HTTP code.
     */
    public ClientRequestException(int httpCode) {
        super();
        this.mHttpCode = httpCode;
    }

    /**
     * Builds a new ClientRequestException object.
     *
     * @param httpCode The HTTP code.
     * @param message  The message.
     */
    public ClientRequestException(int httpCode, String message) {
        super(message);
        this.mHttpCode = httpCode;
    }

    /**
     * Builds a new ClientRequestException object.
     *
     * @param httpCode The HTTP code.
     * @param message  The message.
     * @param cause    The root cause of this exception.
     */
    public ClientRequestException(int httpCode, String message, Throwable cause) {
        super(message, cause);
        this.mHttpCode = httpCode;
    }

    /**
     * Builds a new ClientRequestException object.
     *
     * @param httpCode The HTTP code.
     * @param cause    The root cause of this exception.
     */
    public ClientRequestException(int httpCode, Throwable cause) {
        super(cause);
        this.mHttpCode = httpCode;
    }
}