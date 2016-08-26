package cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 10/08/16.
 */

public class BaseResponse {
    @SerializedName("result")
    public String result;
    @SerializedName("message")
    public String message;
}
