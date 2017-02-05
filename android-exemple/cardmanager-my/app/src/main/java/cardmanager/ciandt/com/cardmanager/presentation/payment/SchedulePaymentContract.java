package cardmanager.ciandt.com.cardmanager.presentation.payment;

import cardmanager.ciandt.com.cardmanager.data.model.User;

public interface SchedulePaymentContract {
    interface View {
        void setPresenter(Presenter presenter);
        void showErrorAccountName(int resId);
        void showErrorDueDate(int resId);
        void showErrorBarCode(int resId);

        void showLoading();
        void hideLoading();

        void showDialogError(String message);
        void showDefaultDialogError(String message);

        void setUser(User user);
        void openPayments();
        void openCamera();
        void systemSchedule(long time);
    }

    interface Presenter {
        void loadLoggedUser();
        void doSchedule(User user, String accountName, String dueDate, String barCode);
        void doOpenCamera();
        boolean validateAccountName(String name, boolean showError);
        boolean validateDueDate(String email, boolean showError);
        boolean validateBarCode(String phoneNumber, boolean showError);
    }
}
