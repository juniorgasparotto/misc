package cardmanager.ciandt.com.cardmanager.presentation.extracts;

import android.content.Context;

import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.manager.UserManager;
import cardmanager.ciandt.com.cardmanager.presentation.card.CardContract;

public class ExtractsPresenter implements ExtractsContract.Presenter {
    private ExtractsContract.View mView;
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

    private final OperationListener<Card> mConfigureToCardCallBack = new OperationListener<Card>() {

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

    public ExtractsPresenter(Context context, ExtractsContract.View view)
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
    public void loadDefaultCard(User user) {
        UserManager manager = new UserManager(this.mContext, Utils.getWebApiRepository(this.mContext));
        manager.getDefaultCard(user, mConfigureToCardCallBack);
    }

    @Override
    public void back() {
        mView.back();
    }
}
