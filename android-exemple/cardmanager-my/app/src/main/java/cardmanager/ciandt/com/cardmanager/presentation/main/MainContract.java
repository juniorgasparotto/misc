package cardmanager.ciandt.com.cardmanager.presentation.main;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;

public interface MainContract {
    interface View {
        void setPresenter(Presenter presenter);
        void showLoading();
        void hideLoading();
        void showDialogError(String message);
        void showDefaultDialogError(String message);

        void configureAppToUser(User user);
        void homeOrMantainLastFragment(boolean forceHome);
        void openMyCards();
        void openExtracts();
        void openPaymentsPending();
        void openAbout();
        void openHome();
        void updateDialogNotificationForPaymentsOverDue(ArrayList<Payment> payments);
    }

    interface Presenter {
        void configureAppToUser();
        void openMyCards();
        void openExtracts();
        void openPaymentsPending();
        void openAbout();
        void openHome();
        void startDialogNotificationForPaymentsOverDue(User user);
        void removePaymentsOverDue(ArrayList<Payment> payments);
    }
}
