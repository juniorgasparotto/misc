package cardmanager.ciandt.com.cardmanager.presentation.payment;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;

public interface PaymentsContract {
    interface View {
        void setPresenter(Presenter presenter);
        void showLoading();
        void hideLoading();
        void showDialogError(String message);
        void showDefaultDialogError(String message);

        void setUser(User user);
        void setPayments(ArrayList<Payment> paymentsSchedule);
        void openAddPayment();
        void removePayment(Payment payment);
        void load();
    }

    interface Presenter {
        void loadLoggedUser();
        void loadPayments(User user);
        void openAddPayment();
        void removePayment(User user, Payment payment);
    }
}
