package cardmanager.ciandt.com.cardmanager.presentation.mycards;

import android.content.Context;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.manager.UserManager;
import cardmanager.ciandt.com.cardmanager.presentation.card.CardContract;

public class MyCardsPresenter implements MyCardsContract.Presenter {
    private MyCardsContract.View mView;
    private Context mContext;

    private final OperationListener<User> mLoadLoggedUserCallBack = new OperationListener<User>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(User user) {
            mView.setUser(user);
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

    private final OperationListener<ArrayList<Card>> mConfigureToCardsCallBack = new OperationListener<ArrayList<Card>>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(ArrayList<Card> card) {
            mView.setCards(card);
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

    private final OperationListener<Card> mSetCardCallBack = new OperationListener<Card>() {

        @Override
        public void onPreExecute() {
            mView.showLoading();
        }

        @Override
        public void onSuccess(Card card) {
            mView.setCard(card);
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

    public MyCardsPresenter(Context context, MyCardsContract.View view)
    {
        this.mContext = context;
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void loadLoggedUser() {
        UserManager manager = new UserManager(this.mContext, Utils.getWebApiRepository(this.mContext));
        manager.getLoggedUser(mLoadLoggedUserCallBack);
    }

    @Override
    public void loadCards(User user) {
        UserManager manager = new UserManager(this.mContext, Utils.getWebApiRepository(this.mContext));
        manager.getCards(user, mConfigureToCardsCallBack);
    }

    @Override
    public void setCardDefault(Card card) {
        UserManager manager = new UserManager(this.mContext, Utils.getWebApiRepository(this.mContext));
        manager.setCardDefault(card, mSetCardCallBack);
    }

    @Override
    public void back() {
        mView.back();
    }
}
