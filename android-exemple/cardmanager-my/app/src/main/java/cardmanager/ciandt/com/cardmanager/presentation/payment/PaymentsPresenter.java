package cardmanager.ciandt.com.cardmanager.presentation.payment;

import android.content.Context;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.business.PaymentBusiness;
import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.manager.PaymentManager;
import cardmanager.ciandt.com.cardmanager.manager.UserManager;
import cardmanager.ciandt.com.cardmanager.presentation.extracts.ExtractsContract;

public class PaymentsPresenter implements PaymentsContract.Presenter {
    private PaymentsContract.View mView;
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

    private final OperationListener<ArrayList<Payment>> mGetPaymentsCallBack = new OperationListener<ArrayList<Payment>>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(ArrayList<Payment> payments) {
            mView.setPayments(payments);
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

    private final OperationListener<Payment> mRemovePaymentCallBack = new OperationListener<Payment>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(Payment payments) {
            mView.removePayment(payments);
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

    public PaymentsPresenter(Context context, PaymentsContract.View view)
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
    public void loadPayments(User user) {
        PaymentManager manager = new PaymentManager(this.mContext);
        manager.doGetPayments(user, mGetPaymentsCallBack);
    }

    @Override
    public void openAddPayment() {
        mView.openAddPayment();
    }

    @Override
    public void removePayment(User user, Payment payment) {
        PaymentManager manager = new PaymentManager(this.mContext);
        manager.doRemovePayment(user, payment, mRemovePaymentCallBack);
    }
}
