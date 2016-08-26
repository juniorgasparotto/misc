package cardmanager.ciandt.com.cardmanager.exception;

/**
 * This class represents an server side error that could be thrown when interacting with an remote
 * repository.
 */
public class ServerUnavailableException extends ServerException {

    public int httpCode;

    /**
     * Builds a new ServerUnavailableException object.
     *
     * @param httpCode The HTTP code.
     */
    public ServerUnavailableException(int httpCode) {
        super();
        this.httpCode = httpCode;
    }

    /**
     * Builds a new ServerUnavailableException object.
     *
     * @param httpCode The HTTP code.
     * @param message  The message.
     */
    public ServerUnavailableException(int httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    /**
     * Builds a new ServerUnavailableException object.
     *
     * @param httpCode The HTTP code.
     * @param message  The message.
     * @param cause    The root cause of this exception.
     */
    public ServerUnavailableException(int httpCode, String message, Throwable cause) {
        super(message, cause);
        this.httpCode = httpCode;
    }

    /**
     * Builds a new ServerUnavailableException object.
     *
     * @param httpCode The HTTP code.
     * @param cause    The root cause of this exception.
     */
    public ServerUnavailableException(int httpCode, Throwable cause) {
        super(cause);
        this.httpCode = httpCode;
    }
}