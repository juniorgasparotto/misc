package cardmanager.ciandt.com.cardmanager.infrastructure;

/**
 * This class is a result of a business operation.
 *
 * @param <TResult> The type of the result object.
 */
public class OperationResult<TResult> {

    /**
     * This is an enum of result types.
     */
    public enum ResultType {
        SUCCESS, ERROR
    }

    public ResultType type;
    public OperationError error;
    public TResult result;
}