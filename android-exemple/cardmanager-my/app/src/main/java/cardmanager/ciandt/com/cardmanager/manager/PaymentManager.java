package cardmanager.ciandt.com.cardmanager.manager;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.business.PaymentBusiness;
import cardmanager.ciandt.com.cardmanager.business.UserBusiness;
import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationResult;

/**
 * Created by root on 09/08/16.
 */

public class PaymentManager {
    private Context mContext;

    public PaymentManager(Context context)
    {
        this.mContext = context;
    }

    public void doGetPayments(final User user, final OperationListener<ArrayList<Payment>> listener)
    {
        final PaymentBusiness business = new PaymentBusiness(mContext);

        AsyncTask<Void, Integer, OperationResult<ArrayList<Payment>>> task = new AsyncTask<Void, Integer, OperationResult<ArrayList<Payment>>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<ArrayList<Payment>> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<ArrayList<Payment>> doInBackground(Void... voids) {
                return business.getPayments(user);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void doRemovePayment(final User user, final Payment payment, final OperationListener<Payment> listener) {
        final PaymentBusiness business = new PaymentBusiness(mContext);

        AsyncTask<Void, Integer, OperationResult<Payment>> task = new AsyncTask<Void, Integer, OperationResult<Payment>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<Payment> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<Payment> doInBackground(Void... voids) {
                return business.removePayment(user, payment);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void doAddPayment(final User user, final Payment payment, final OperationListener<Payment> listener) {
        final PaymentBusiness business = new PaymentBusiness(mContext);

        AsyncTask<Void, Integer, OperationResult<Payment>> task = new AsyncTask<Void, Integer, OperationResult<Payment>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<Payment> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<Payment> doInBackground(Void... voids) {
                return business.addPayment(user, payment);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void doGetPaymentsOverDue(final User user, final OperationListener<ArrayList<Payment>> listener) {
        final PaymentBusiness business = new PaymentBusiness(mContext);

        AsyncTask<Void, Integer, OperationResult<ArrayList<Payment>>> task = new AsyncTask<Void, Integer, OperationResult<ArrayList<Payment>>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<ArrayList<Payment>> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<ArrayList<Payment>> doInBackground(Void... voids) {
                return business.getPaymentsOverDue(user);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void doRemovePaymentsOverDue(final ArrayList<Payment> payments, final OperationListener<Void> listener) {
        final PaymentBusiness business = new PaymentBusiness(mContext);

        AsyncTask<Void, Integer, OperationResult<Void>> task = new AsyncTask<Void, Integer, OperationResult<Void>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<Void> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<Void> doInBackground(Void... voids) {
                return business.removePaymentsOverDue(payments);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
