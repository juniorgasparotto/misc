package cardmanager.ciandt.com.cardmanager.data.repository.webapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.Purchase;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.CardMap;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.PurchaseMap;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.UserMap;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.request.LoginRequest;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.response.LoginResponse;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.request.SignUpRequest;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.response.SignUpResponse;
import cardmanager.ciandt.com.cardmanager.exception.ClientRequestException;
import cardmanager.ciandt.com.cardmanager.exception.ServerException;
import cardmanager.ciandt.com.cardmanager.exception.ServerUnavailableException;
import cardmanager.ciandt.com.cardmanager.infrastructure.Logger;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationResult;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class WebApiRepository {
    private static final int HTTP_STATUS_CLIENT_ERROR_RANGE_START = 400;
    private static final int HTTP_STATUS_CLIENT_ERROR_RANGE_END = 499;
    private static final int HTTP_STATUS_SERVER_ERROR_RANGE_START = 500;
    private static final int HTTP_STATUS_SERVER_ERROR_RANGE_END = 599;
    private static final String SUCCESS_RESPONSE_VALUE = "success";

    private static final String TAG = WebApiRepository.class.getSimpleName();
    private IServer mServer;
    protected Retrofit mRetrofit;

    public interface IServer {
        @POST("login")
        Call<LoginResponse> login(@Body LoginRequest request);

        @POST("signup")
        Call<SignUpResponse> signUp(@Body SignUpRequest request);
    }

    public WebApiRepository(String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mServer = mRetrofit.create(IServer.class);
    }

    public OperationResult<User> login(String email, String pwd)
    {
        OperationResult<User> result = new OperationResult<User>();

        LoginRequest request = new LoginRequest();
        request.email = email;
        request.password = pwd;

        Call<LoginResponse> call = mServer.login(request);
        LoginResponse response = null;

        try {
            Response<LoginResponse> callResponse = call.execute();

            if (callResponse.isSuccessful()) {
                response = callResponse.body();
                if (response.result.equals( SUCCESS_RESPONSE_VALUE )) {
                    result.type = OperationResult.ResultType.SUCCESS;
                    result.result = toUser(response.user, response.cards);
                }
                else {
                    result.type = OperationResult.ResultType.ERROR;
                    result.error = new OperationError();
                    result.error.code = OperationError.ERROR_CODE_SERVER_WITH_MESSAGE;
                    result.error.message = response.message;
                }
            } else {
                result.type = OperationResult.ResultType.ERROR;

                try {
                    checkResponseError(callResponse);
                } catch (ClientRequestException e) {
                    Logger.error(TAG, e.getLocalizedMessage(), e);
                    result.error = new OperationError();
                    result.error.code = OperationError.ERROR_CODE_CLIENT_REQUEST;
                    result.error.message = e.getMessage();
                } catch (ServerUnavailableException e) {
                    Logger.error(TAG, e.getLocalizedMessage(), e);
                    result.error = new OperationError();
                    result.error.code = OperationError.ERROR_CODE_SERVER_UNAVAILABLE;
                    result.error.message = e.getMessage();
                }
            }
        } catch (IOException e) {
            result.type = OperationResult.ResultType.ERROR;
            Logger.error(TAG, e.getLocalizedMessage(), e);
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = e.getMessage();
        }

        return result;
    }

    public OperationResult<Boolean> signUp(String name, String email, String cellNumber, String cardNumber)
    {
        OperationResult<Boolean> result = new OperationResult<Boolean>();
        result.result = false;
        result.type = OperationResult.ResultType.ERROR;

        SignUpRequest request = new SignUpRequest(name, email, cellNumber, cardNumber);

        Call<SignUpResponse> call = mServer.signUp(request);
        SignUpResponse response = null;

        try {
            Response<SignUpResponse> callResponse = call.execute();

            if (callResponse.isSuccessful()) {
                response = callResponse.body();
                if (response.result.equals( SUCCESS_RESPONSE_VALUE )) {
                    result.type = OperationResult.ResultType.SUCCESS;
                    result.result = true;
                }
                else {
                    result.error = new OperationError();
                    result.error.code = OperationError.ERROR_CODE_SERVER_WITH_MESSAGE;
                    result.error.message = response.message;
                }
            } else {
                try {
                    checkResponseError(callResponse);
                } catch (ClientRequestException e) {
                    Logger.error(TAG, e.getLocalizedMessage(), e);
                    result.error = new OperationError();
                    result.error.code = OperationError.ERROR_CODE_CLIENT_REQUEST;
                    result.error.message = e.getMessage();
                } catch (ServerUnavailableException e) {
                    Logger.error(TAG, e.getLocalizedMessage(), e);
                    result.error = new OperationError();
                    result.error.code = OperationError.ERROR_CODE_SERVER_UNAVAILABLE;
                    result.error.message = e.getMessage();
                }
            }
        } catch (IOException e) {
            Logger.error(TAG, e.getLocalizedMessage(), e);
            result.error = new OperationError();
            result.error.code = OperationError.ERROR_CODE_UNKNOWN;
            result.error.message = e.getMessage();
        }

        return result;
    }


    private User toUser(UserMap map, List<CardMap> cardsMap)
    {
        if (map != null) {
            User user = new User();
            user.cards = new ArrayList<Card>();

            user.name = map.name;
            user.profileImagePath = map.profileImagePath;

            if (cardsMap != null)
                for (CardMap cardMap : cardsMap)
                    user.cards.add(toCard(cardMap));

            return user;
        }
        return null;
    }

    private Card toCard(CardMap map)
    {
        if (map != null) {
            Card card = new Card();
            card.purchases = new ArrayList<Purchase>();

            card.number = map.number;
            card.flagImagePath = map.flagImagePath;
            card.cardImagePath = map.cardImagePath;
            card.color = map.color;
            card.dueDate = map.dueDate;
            card.limitAvailable = map.limitAvailable;
            card.invoiceAmount = map.invoiceAmount;

            if (map.purchases != null)
                for (PurchaseMap purchaseMap : map.purchases)
                    card.purchases.add(toPurchase(purchaseMap));

            return card;
        }
        return null;
    }

    private Purchase toPurchase(PurchaseMap map)
    {
        if (map != null) {
            Purchase purchase = new Purchase();
            purchase.date = map.date;
            purchase.spent = map.spent;
            purchase.value = map.value;
            return purchase;
        }
        return null;
    }

    /**
     * Verifies if the response has a client side error.
     *
     * @param response the HTTP response.
     * @return true if the response has an error in range (400, 499), false otherwise.
     */
    private boolean hasClientError(Response response) {
        return response.code() >= HTTP_STATUS_CLIENT_ERROR_RANGE_START
                && response.code() <= HTTP_STATUS_CLIENT_ERROR_RANGE_END;
    }

    /**
     * Verifies if the response has a server side error.
     *
     * @param response the HTTP response.
     * @return true if the response has an error in range (500, 599), false otherwise.
     */
    private boolean hasServerError(Response response) {
        return response.code() >= HTTP_STATUS_SERVER_ERROR_RANGE_START
                && response.code() <= HTTP_STATUS_SERVER_ERROR_RANGE_END;
    }

    /**
     * Checks if the response has any errors.
     *
     * @param response the HTTP response.
     * @throws ClientRequestException     if the response has an error on client side.
     * @throws ServerUnavailableException if the response has an error on server side.
     */
    protected void checkResponseError(Response response)
            throws ClientRequestException, ServerUnavailableException {
        if (hasClientError(response)) {
            throw new ClientRequestException(response.code(), response.message());
        } else if (hasServerError(response)) {
            throw new ServerUnavailableException(response.code(), response.message());
        }
    }
}