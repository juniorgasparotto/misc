package cardmanager.ciandt.com.cardmanager.business;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.data.repository.sharedpreferences.SharedPreferencesRepository;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationResult;

/**
 * Created by root on 09/08/16.
 */

public class PaymentBusiness {
    private SharedPreferencesRepository mSharedPreferencesRepository;
    private final String STORE_CONTEXT_PAYMENT = "payment_context";
    private final String STORE_KEY_PAYMENTS = "payments";
    private Context mContext;

    public PaymentBusiness(Context context)
    {
        this.mContext = context;
        this.mSharedPreferencesRepository = new SharedPreferencesRepository(this.mContext, STORE_CONTEXT_PAYMENT);
    }

    public OperationResult<ArrayList<Payment>> getPayments(User user)
    {
        OperationResult<ArrayList<Payment>> result = new OperationResult<>();

        try {
            if (user == null) {
                result.type = OperationResult.ResultType.ERROR;
                result.error = new OperationError();
                result.error.code = OperationError.ERROR_CODE_SERVER_WITH_MESSAGE;
                result.error.message = "Not logged";
            } else {
                result.result = this.mSharedPreferencesRepository.getList(STORE_KEY_PAYMENTS, Payment[].class);

                result.result = new ArrayList<>();
                Payment payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);

                payment = new Payment();
                payment.date = new Date();
                payment.name = "Junior";
                payment.id = java.util.UUID.randomUUID().toString();
                result.result.add(payment);





                result.type = OperationResult.ResultType.SUCCESS;
            }
        }
        catch (Exception ex)
        {
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = ex.toString();
            result.type = OperationResult.ResultType.ERROR;
        }

        return result;
    }

    public OperationResult<Payment> removePayment(User user, Payment payment) {
        OperationResult<Payment> result = new OperationResult<>();

        try {
            if (user == null) {
                result.type = OperationResult.ResultType.ERROR;
                result.error = new OperationError();
                result.error.code = OperationError.ERROR_CODE_SERVER_WITH_MESSAGE;
                result.error.message = "Not logged";
            } else {
                ArrayList<Payment> payments = this.mSharedPreferencesRepository.getList(STORE_KEY_PAYMENTS, Payment[].class);

                if (payments != null) {
                    for (Payment item : payments)
                        if (item.id.equals(payment.id))
                            payments.remove(item);
                }

                this.mSharedPreferencesRepository.insertOrUpdate(STORE_KEY_PAYMENTS, payments);
                result.type = OperationResult.ResultType.SUCCESS;
                result.result = payment;
            }
        }
        catch (Exception ex)
        {
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = ex.toString();
            result.type = OperationResult.ResultType.ERROR;
        }

        return result;
    }

    public OperationResult<Payment> addPayment(User user, Payment payment) {
        OperationResult<Payment> result = new OperationResult<>();

        try {
            if (user == null) {
                result.type = OperationResult.ResultType.ERROR;
                result.error = new OperationError();
                result.error.code = OperationError.ERROR_CODE_SERVER_WITH_MESSAGE;
                result.error.message = "Not logged";
            } else {
                ArrayList<Payment> payments = this.mSharedPreferencesRepository.getList(STORE_KEY_PAYMENTS, Payment[].class);

                if (payments == null)
                    payments = new ArrayList<Payment>();

                payments.add(payment);
                this.mSharedPreferencesRepository.insertOrUpdate(STORE_KEY_PAYMENTS, payments);

                result.type = OperationResult.ResultType.SUCCESS;
                result.result = payment;
            }
        }
        catch (Exception ex)
        {
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = ex.toString();
            result.type = OperationResult.ResultType.ERROR;
        }

        return result;
    }
}
