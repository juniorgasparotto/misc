package cardmanager.ciandt.com.cardmanager.presentation.payment;

import android.content.Context;
import android.text.TextUtils;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.manager.PaymentManager;
import cardmanager.ciandt.com.cardmanager.manager.UserManager;

public class SchedulePaymentPresenter implements SchedulePaymentContract.Presenter {
    private SchedulePaymentContract.View mView;
    private Context mContext;

    private final OperationListener<User> mLoadLoggedUserCallBack = new OperationListener<User>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(User user) {
            mView.setUser(user);
        }

        @Override
        public void onError(OperationError error) {
            if (error.code == OperationError.ERROR_CODE_SERVER_WITH_MESSAGE) {
                mView.showDialogError(error.message);
            } else {
                mView.showDefaultDialogError(error.message);
            }
        }

        @Override
        public void onCancel() {
            mView.hideLoading();
        }

        @Override
        public void onPostExecute() {
            mView.hideLoading();
        }
    };

    private final OperationListener<Payment> mCallBack = new OperationListener<Payment>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(Payment result) {
            mView.systemSchedule(result.date.getTime());
            mView.openPayments();
        }

        @Override
        public void onError(OperationError error) {
            if (error.code == OperationError.ERROR_CODE_SERVER_WITH_MESSAGE) {
                mView.showDialogError(error.message);
            } else {
                mView.showDefaultDialogError(error.message);
            }
        }

        @Override
        public void onCancel() {
            mView.hideLoading();
        }

        @Override
        public void onPostExecute() {
            mView.hideLoading();
        }
    };

    public SchedulePaymentPresenter(Context context, SchedulePaymentContract.View view)
    {
        this.mContext = context;
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void loadLoggedUser() {
        UserManager manager = new UserManager(this.mContext, Utils.getWebApiRepository(this.mContext));
        manager.getLoggedUser(mLoadLoggedUserCallBack);
    }

    @Override
    public void doSchedule(User user, String accountName, String dueDate, String barCode) {
        boolean isError = false;

        if (!this.validateAccountName(accountName, true)) isError = true;
        if (!this.validateBarCode(barCode, true)) isError = true;
        if (!this.validateDueDate(dueDate, true)) isError = true;

        if (!isError)
        {
            PaymentManager manager = new PaymentManager(this.mContext);
            Payment payment = new Payment();
            payment.name = accountName;
            payment.date = Utils.tryParseDate(this.mContext, dueDate, R.string.schedule_format_date_and_time);
            payment.barCode = barCode;
            manager.doAddPayment(user, payment, mCallBack);
        }
    }

    @Override
    public void doOpenCamera() {
        mView.openCamera();
    }

    @Override
    public boolean validateAccountName(String accountName, boolean showError) {
        boolean isOK = true;

        if (TextUtils.isEmpty(accountName)) {

            if (showError)
                mView.showErrorAccountName(R.string.schedule_payment_error_account_name_empty);

            isOK = false;
        } else if (accountName.length() > 100) {

            if (showError) {
                mView.showErrorAccountName(R.string.schedule_payment_error_account_name_invalid);
            }

            isOK = false;
        }

        return isOK;
    }

    @Override
    public boolean validateDueDate(String date, boolean showError) {boolean isOK = true;

        if (TextUtils.isEmpty(date)) {

            if (showError)
                mView.showErrorDueDate(R.string.schedule_payment_error_date_empty);

            isOK = false;
        } else if (Utils.tryParseDate(this.mContext, date, R.string.schedule_format_date_and_time) == null) {

            if (showError)
                mView.showErrorDueDate(R.string.schedule_payment_error_date_invalid);

            isOK = false;
        }

        return isOK;
    }

    @Override
    public boolean validateBarCode(String barCode, boolean showError) {
        boolean isOK = true;

        if (TextUtils.isEmpty(barCode)) {

            if (showError)
                mView.showErrorBarCode(R.string.schedule_payment_error_bar_code_empty);

            isOK = false;
        } else if (barCode.length() > 100) {

            if (showError)
                mView.showErrorBarCode(R.string.schedule_payment_error_bar_code_invalid);

            isOK = false;
        }

        return isOK;
    }
}
