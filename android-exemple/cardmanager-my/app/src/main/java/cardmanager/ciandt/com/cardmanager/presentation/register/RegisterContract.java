package cardmanager.ciandt.com.cardmanager.presentation.register;

public interface RegisterContract {
    interface View {
        void setPresenter(Presenter presenter);
        void showErrorName(int resId);
        void showErrorEmail(int resId);
        void showErrorPhoneNumber(int resId);
        void showErrorCardNumber(int resId);
        void showErrorTermsNotAccept(int resId);

        void showLoading();
        void hideLoading();

        void showDialogError(String message);
        void showDefaultDialogError(String message);

        void openMainActivity();
        void openLoginActivity();
        void openUseTerms();
        void openCamera();
    }

    interface Presenter {
        void doRegister(String name, String email, String phoneNumber, String cardNumber, boolean isAcceptedTerms);
        void doOpenUseTerms();
        void doOpenCamera();
        boolean validateName(String name, boolean showError);
        boolean validateEmail(String email, boolean showError);
        boolean validatePhoneNumber(String phoneNumber, boolean showError);
        boolean validateCardNumber(String cardNumber, boolean showError);
        boolean validateTerms(boolean isAcceptedTerms, boolean showError);
    }
}
