package cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.response;

/**
 * Created by root on 10/08/16.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.CardMap;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.UserMap;

/**
 * This class models a login response body.
 */
public class LoginResponse extends BaseResponse {
    @SerializedName("user")
    public UserMap user;
    @SerializedName("cards")
    public List<CardMap> cards;
}