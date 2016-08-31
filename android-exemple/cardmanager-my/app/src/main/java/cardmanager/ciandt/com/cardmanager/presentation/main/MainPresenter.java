package cardmanager.ciandt.com.cardmanager.presentation.main;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.manager.PaymentManager;
import cardmanager.ciandt.com.cardmanager.manager.UserManager;
import cardmanager.ciandt.com.cardmanager.presentation.register.RegisterContract;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private Context mContext;
    private final OperationListener<User> mConfigureAppToUserCallBack = new OperationListener<User>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(User user) {
            mView.configureAppToUser(user);
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

    private final OperationListener<ArrayList<Payment>> mUpdatePaymentsOverDueCallBack = new OperationListener<ArrayList<Payment>>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(ArrayList<Payment> payments) {
            mView.updateDialogNotificationForPaymentsOverDue(payments);
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

    private final OperationListener<Void> mRemovePaymentsOverDueCallBack = new OperationListener<Void>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(Void payments) {

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

    public MainPresenter(Context context, MainContract.View view)
    {
        this.mContext = context;
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void configureAppToUser() {
        UserManager manager = new UserManager(this.mContext, Utils.getWebApiRepository(this.mContext));
        manager.getLoggedUser(mConfigureAppToUserCallBack);
    }

    @Override
    public void openHome() {
        mView.openHome();
    }

    @Override
    public void startDialogNotificationForPaymentsOverDue(User user) {
        PaymentManager manager = new PaymentManager(this.mContext);
        manager.doGetPaymentsOverDue(user, mUpdatePaymentsOverDueCallBack);
    }

    @Override
    public void removePaymentsOverDue(ArrayList<Payment> payments) {
        PaymentManager manager = new PaymentManager(this.mContext);
        manager.doRemovePaymentsOverDue(payments, mRemovePaymentsOverDueCallBack);
    }

    @Override
    public void openMyCards() {
        mView.openMyCards();
    }

    @Override
    public void openExtracts() {
        mView.openExtracts();
    }

    @Override
    public void openPaymentsPending() {
        mView.openPaymentsPending();
    }

    @Override
    public void openAbout() {
        mView.openAbout();
    }
}
