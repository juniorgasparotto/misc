package cardmanager.ciandt.com.cardmanager.presentation.register;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.manager.UserManager;
import cardmanager.ciandt.com.cardmanager.presentation.login.LoginContract;
import io.card.payment.CardIOActivity;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;
    private Context mContext;
    private final OperationListener<Boolean> mCallBack = new OperationListener<Boolean>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(Boolean result) {
            mView.openLoginActivity();
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

    public RegisterPresenter(Context context, RegisterContract.View view)
    {
        this.mContext = context;
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void doRegister(String name, String email, String phoneNumber, String cardNumber, boolean isAcceptedTerms) {
        boolean isError = false;

        if (!this.validateName(name, true)) isError = true;
        if (!this.validateEmail(email, true)) isError = true;
        if (!this.validatePhoneNumber(phoneNumber, true)) isError = true;
        if (!this.validateCardNumber(cardNumber, true)) isError = true;
        if (!this.validateTerms(isAcceptedTerms, true)) isError = true;

        String internetError = Utils.getInternetAccessError(mContext);

        if (internetError != null) {
            isError = true;
            mView.showDialogError(internetError);
        }

        if (!isError)
        {
            UserManager manager = new UserManager(this.mContext, Utils.getWebApiRepository(this.mContext));
            manager.doRegister(name, email, phoneNumber, cardNumber, mCallBack);
        }
    }

    @Override
    public boolean validateName(String name, boolean showError) {
        boolean isOK = true;

        if (TextUtils.isEmpty(name)) {

            if (showError)
                mView.showErrorName(R.string.register_error_name_empty);

            isOK = false;
        } else if (name.length() > 100) {
            if (showError)
                mView.showErrorName(R.string.register_error_name_invalid);

            isOK = false;
        } else {
            String[] words = name.split(" ");
            if (words.length < 2) {
                if (showError)
                    mView.showErrorName(R.string.register_error_name_without_last_name);

                isOK = false;
            }
            else {
                for (String word : words) {
                    if (word.length() < 3) {

                        if (showError)
                            mView.showErrorName(R.string.register_error_name_less_three_chars);

                        isOK = false;
                        break;
                    }
                }
            }
        }

        return isOK;
    }

    @Override
    public boolean validateEmail(String email, boolean showError) {
        boolean isOK = true;

        if (TextUtils.isEmpty(email)) {

            if (showError)
                mView.showErrorEmail(R.string.register_error_email_empty);

            isOK = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.length() > 100) {

            if (showError)
                mView.showErrorEmail(R.string.register_error_email_invalid);

            isOK = false;
        }

        return isOK;
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber, boolean showError) {
        boolean isOK = true;

        if (TextUtils.isEmpty(phoneNumber)) {

            if (showError)
                mView.showErrorPhoneNumber(R.string.register_error_phone_number_empty);

            isOK = false;
        } else if (phoneNumber.length() != 11) {

            if (showError)
                mView.showErrorPhoneNumber(R.string.register_error_phone_number_empty_invalid);

            isOK = false;
        }

        return isOK;
    }

    @Override
    public boolean validateCardNumber(String cardNumber, boolean showError) {
        boolean isOK = true;

        if (TextUtils.isEmpty(cardNumber)) {

            if (showError)
                mView.showErrorCardNumber(R.string.register_error_card_number_empty);

            isOK = false;
        } else if (cardNumber.length() > 20 || !TextUtils.isDigitsOnly(cardNumber)) {

            if (showError)
                mView.showErrorCardNumber(R.string.register_error_card_number_empty_invalid);

            isOK = false;
        }

        return isOK;
    }

    @Override
    public boolean validateTerms(boolean isAcceptedTerms, boolean showError) {
        boolean isOK = true;

        if (!isAcceptedTerms) {

            if (showError)
                mView.showErrorTermsNotAccept(R.string.register_error_terms_not_accepted_invalid);

            isOK = false;
        }

        return isOK;
    }

    @Override
    public void doOpenUseTerms() {
        mView.openUseTerms();
    }

    @Override
    public void doOpenCamera() {
        mView.openCamera();
    }
}
