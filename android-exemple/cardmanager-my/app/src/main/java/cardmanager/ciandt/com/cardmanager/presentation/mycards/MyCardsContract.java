package cardmanager.ciandt.com.cardmanager.presentation.mycards;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;

public interface MyCardsContract {
    interface View {
        void setPresenter(Presenter presenter);
        void showLoading();
        void hideLoading();
        void showDialogError(String message);
        void showDefaultDialogError(String message);

        void setUser(User user);
        void setCards(ArrayList<Card> cards);
        void setCard(Card card);
        void back();
    }

    interface Presenter {
        void loadLoggedUser();
        void loadCards(User user);
        void setCardDefault(Card card);
        void back();
    }
}
