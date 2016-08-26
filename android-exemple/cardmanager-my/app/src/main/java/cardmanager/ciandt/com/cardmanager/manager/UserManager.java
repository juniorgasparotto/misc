package cardmanager.ciandt.com.cardmanager.manager;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.business.UserBusiness;
import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.data.repository.sharedpreferences.SharedPreferencesRepository;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.WebApiRepository;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationResult;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;

/**
 * Created by root on 09/08/16.
 */

public class UserManager {
    private WebApiRepository mWebApiRepository;
    private Context mContext;

    public UserManager(Context context, WebApiRepository repository)
    {
        this.mContext = context;
        this.mWebApiRepository = repository;
    }

    public void doLogin(final String email, final String pwd, final OperationListener<User> listener)
    {
        final UserBusiness business = new UserBusiness(mContext, this.mWebApiRepository);

        AsyncTask<Void, Integer, OperationResult<User>> task = new AsyncTask<Void, Integer, OperationResult<User>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<User> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<User> doInBackground(Void... voids) {
                return business.login(email, pwd);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void doRegister(final String name, final String email, final String cellNumber, final String cardNumber, final OperationListener<Boolean> listener)
    {
        final UserBusiness business = new UserBusiness(this.mContext, this.mWebApiRepository);

        AsyncTask<Void, Integer, OperationResult<Boolean>> task = new AsyncTask<Void, Integer, OperationResult<Boolean>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<Boolean> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<Boolean> doInBackground(Void... voids) {
                return business.signUp(name, email, cellNumber, cardNumber);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void getLoggedUser(final OperationListener<User> listener)
    {
        final UserBusiness business = new UserBusiness(this.mContext, this.mWebApiRepository);

        AsyncTask<Void, Integer, OperationResult<User>> task = new AsyncTask<Void, Integer, OperationResult<User>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<User> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<User> doInBackground(Void... voids) {
                return business.getLoggedUser();
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void getDefaultCard(final User user, final OperationListener<Card> listener) {
        final UserBusiness business = new UserBusiness(this.mContext, this.mWebApiRepository);

        AsyncTask<Void, Integer, OperationResult<Card>> task = new AsyncTask<Void, Integer, OperationResult<Card>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<Card> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<Card> doInBackground(Void... voids) {
                return business.getDefaultCard(user);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void setCardDefault(final Card card, final OperationListener<Card> listener) {
        final UserBusiness business = new UserBusiness(this.mContext, this.mWebApiRepository);

        AsyncTask<Void, Integer, OperationResult<Card>> task = new AsyncTask<Void, Integer, OperationResult<Card>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<Card> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<Card> doInBackground(Void... voids) {
                return business.setDefaultCard(card);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void getCards(final User user, final OperationListener<ArrayList<Card>> listener) {
        final UserBusiness business = new UserBusiness(this.mContext, this.mWebApiRepository);

        AsyncTask<Void, Integer, OperationResult<ArrayList<Card>>> task = new AsyncTask<Void, Integer, OperationResult<ArrayList<Card>>>() {
            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(OperationResult<ArrayList<Card>> result) {
                switch (result.type) {
                    case SUCCESS:
                        listener.onSuccess(result.result);
                        break;
                    case ERROR:
                        listener.onError(result.error);
                        break;
                }

                listener.onPostExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                listener.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                listener.onCancel();
            }

            @Override
            protected OperationResult<ArrayList<Card>> doInBackground(Void... voids) {
                return business.getCards(user);
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
