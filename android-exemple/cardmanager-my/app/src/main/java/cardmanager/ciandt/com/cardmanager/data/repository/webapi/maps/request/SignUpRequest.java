package cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.request;

import com.google.gson.annotations.SerializedName;

/**
 * This class models a sign up request body.
 */
public class SignUpRequest {

    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("phone_number")
    public String cellNumber;
    @SerializedName("card_number")
    public String cardNumber;

    public SignUpRequest(String name, String email, String cellNumber, String cardNumber) {
        this.name = name;
        this.email = email;
        this.cellNumber = cellNumber;
        this.cardNumber = cardNumber;
    }
}