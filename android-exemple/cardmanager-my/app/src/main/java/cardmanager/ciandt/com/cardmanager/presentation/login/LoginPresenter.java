package cardmanager.ciandt.com.cardmanager.presentation.login;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.manager.UserManager;

public class LoginPresenter implements LoginContract.Presenter {
    private final Context mContext;
    private final LoginContract.View mView;
    private final OperationListener<User> mCallBack = new OperationListener<User>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(User result) {
            mView.openMainActivity();
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

    public LoginPresenter(Context context, LoginContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.mContext = context;
    }

    @Override
    public void doLogin(String email, String pwd) {
        boolean isError = false;
        if (TextUtils.isEmpty(email)) {
            mView.showErrorEmail(R.string.txt_login_name_empty);
            isError = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.length() > 100) {
            mView.showErrorEmail(R.string.txt_login_name_invalid);
            isError = true;
        }

        if (TextUtils.isEmpty(pwd) || pwd.length() > 6) {
            mView.showErrorPwd(R.string.txt_login_pwd_empty);
            isError = true;
        }

        String internetError = Utils.getInternetAccessError(mContext);

        if (internetError != null) {
            isError = true;
            mView.showDialogError(internetError);
        }

        if (!isError)
        {
            UserManager manager = new UserManager(this.mContext, Utils.getWebApiRepository(this.mContext));
            manager.doLogin(email, pwd, mCallBack);
        }
    }

    @Override
    public void doNewRegister(String email) {
        this.mView.openNewRegisterActivity();
    }
}
