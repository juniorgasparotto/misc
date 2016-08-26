package cardmanager.ciandt.com.cardmanager.presentation.card;

import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;

public interface CardContract {
    interface View {
        void setPresenter(Presenter presenter);
        void showLoading();
        void hideLoading();
        void showDialogError(String message);
        void showDefaultDialogError(String message);

        void setUser(User user);
        void setCard(Card card);
        void back();
    }

    interface Presenter {
        void loadLoggedUser();
        void loadDefaultCard(User user);
        void back();
    }
}
