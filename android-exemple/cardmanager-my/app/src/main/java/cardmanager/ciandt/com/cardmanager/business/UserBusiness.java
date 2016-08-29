package cardmanager.ciandt.com.cardmanager.business;

import android.content.Context;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.data.repository.sharedpreferences.SharedPreferencesRepository;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.WebApiRepository;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationResult;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;

/**
 * Created by root on 09/08/16.
 */

public class UserBusiness {
    private WebApiRepository mWebApiRepository;
    private SharedPreferencesRepository mSharedPreferencesRepository;
    private final String STORE_CONTEXT_USER = "user_context";
    private final String STORE_KEY_LOGGED = "user_logged";
    private final String STORE_KEY_DEFAULT_CARD = "card_default";
    private Context mContext;

    public UserBusiness(Context context, WebApiRepository repository)
    {
        this.mContext = context;
        this.mWebApiRepository = repository;
        this.mSharedPreferencesRepository = new SharedPreferencesRepository(this.mContext, STORE_CONTEXT_USER);
    }

    public OperationResult<User> login(String email, String pwd)
    {
        OperationResult<User> opUser = this.mWebApiRepository.login(email, pwd);

        if (opUser != null && opUser.result != null)
        {
            mSharedPreferencesRepository.insertOrUpdate(STORE_KEY_LOGGED, opUser.result);
        }

        return opUser;
    }

    public OperationResult<Boolean> signUp(String name, String email, String cellNumber, String cardNumber)
    {
        return this.mWebApiRepository.signUp(name, email, cellNumber, cardNumber);
    }

    public OperationResult<User> getLoggedUser() {
        OperationResult<User> result = new OperationResult<User>();

        try {
            result.result = this.mSharedPreferencesRepository.get(STORE_KEY_LOGGED, User.class);
            if (result.result == null) {
                result.type = OperationResult.ResultType.ERROR;
                result.error = new OperationError();
                result.error.code = OperationError.ERROR_CODE_SERVER_WITH_MESSAGE;
                result.error.message = "Not logged";
            } else {
                result.type = OperationResult.ResultType.SUCCESS;
            }
        }
        catch (Exception ex)
        {
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = ex.toString();
            result.type = OperationResult.ResultType.ERROR;
        }

        return result;
    }

    public OperationResult<Card> getDefaultCard(User user) {
        OperationResult<Card> result = new OperationResult<>();

        try {
            result.type = OperationResult.ResultType.SUCCESS;
            result.error = null;

            result.result = this.mSharedPreferencesRepository.get(STORE_KEY_DEFAULT_CARD, Card.class);
            if (result.result == null && user != null && user.cards != null && user.cards.size() > 0) {
                result.result = user.cards.get(0);
                this.setDefaultCard(result.result);
            }
        }
        catch (Exception ex) {
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = ex.toString();
            result.type = OperationResult.ResultType.ERROR;
        }

        return result;
    }

    public OperationResult<Card> setDefaultCard(Card card)
    {
        OperationResult<Card> result = new OperationResult<Card>();
        try {
            mSharedPreferencesRepository.insertOrUpdate(STORE_KEY_DEFAULT_CARD, card);
            result.type = OperationResult.ResultType.SUCCESS;
            result.result = card;
        }
        catch (Exception ex)
        {
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = ex.toString();
            result.type = OperationResult.ResultType.ERROR;
        }

        return result;
    }

    public OperationResult<ArrayList<Card>> getCards(User user) {
        OperationResult<ArrayList<Card>> result = new OperationResult<ArrayList<Card>>();
        try {
            result.type = OperationResult.ResultType.SUCCESS;
            result.result = user.cards;
        }
        catch (Exception ex)
        {
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = ex.toString();
            result.type = OperationResult.ResultType.ERROR;
        }

        return result;
    }
}
