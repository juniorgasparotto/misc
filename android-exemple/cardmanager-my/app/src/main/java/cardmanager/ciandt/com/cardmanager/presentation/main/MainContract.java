package cardmanager.ciandt.com.cardmanager.presentation.main;

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
    }

    interface Presenter {
        void configureAppToUser();
        void openMyCards();
        void openExtracts();
        void openPaymentsPending();
        void openAbout();
        void openHome();
    }
}
