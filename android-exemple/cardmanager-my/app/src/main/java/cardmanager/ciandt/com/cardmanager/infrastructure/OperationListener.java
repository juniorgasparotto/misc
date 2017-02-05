package cardmanager.ciandt.com.cardmanager.infrastructure;

/**
 * This class is a listener to business operations.
 * <p>
 * Abstract class and not an Interface, so the client does not need to follow the exact contract
 * provided by this class.
 * This will prevent empty implementations on presentation layer.
 *
 * @param <TResult> The type of the result object.
 */
public abstract class OperationListener<TResult> {

    /**
     * Called before the operation start.
     */
    public void onPreExecute() {
    }

    /**
     * Called when the operation finishes with success.
     *
     * @param result The result object.
     */
    public void onSuccess(TResult result) {
    }

    /**
     * Called when occur any error during the operation execution.
     *
     * @param error The <code>OperationError</code> object with detailed information about de error.
     */
    public void onError(OperationError error) {
    }

    /**
     * Called when the operation was cancelled.
     */
    public void onCancel() {
    }

    /**
     * Called after the operation finished, no matter whats is your result.
     */
    public void onPostExecute() {
    }

    /**
     * Called when operation progress changes.
     *
     * @param progress The progress percentage.
     */
    public void onProgressUpdate(Integer... progress) {
    }
}