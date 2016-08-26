package cardmanager.ciandt.com.cardmanager.presentation.login;

public interface LoginContract {
    interface View {
        void setPresenter(Presenter presenter);
        void showErrorEmail(int resId);
        void showErrorPwd(int resId);
        void showLoading();
        void openMainActivity();
        void hideLoading();
        void showDialogError(String message);
        void showDefaultDialogError(String message);
        void openNewRegisterActivity();
    }

    interface Presenter {
        void doLogin(String email, String pwd);
        void doNewRegister(String email);
    }
}
