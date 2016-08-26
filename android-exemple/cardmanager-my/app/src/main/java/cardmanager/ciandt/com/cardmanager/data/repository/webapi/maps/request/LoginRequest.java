package cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.request;

/**
 * Created by root on 10/08/16.
 */

import com.google.gson.annotations.SerializedName;

/**
 * This class models a login request body.
 */
public class LoginRequest {
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
