package cardmanager.ciandt.com.cardmanager.infrastructure;

/**
 * This class is an error of a business operation.
 */
public class OperationError {

    public static final int ERROR_CODE_SERVER_WITH_MESSAGE = 1;
    public static final int ERROR_CODE_CLIENT_REQUEST = 2;
    public static final int ERROR_CODE_SERVER_UNAVAILABLE = 3;
    public static final int ERROR_CODE_UNKNOWN = 4;
    public static final int ERROR_NOT_AUTHORIZED = 5;

//    public static final int ERROR_CAN_NOT_VERIFY_INTERNET_PERMISSION = 6;
//    public static final int ERROR_WITHOUT_INTERNET_PERMISSION = 5;

    public int code;
    public String message;
}