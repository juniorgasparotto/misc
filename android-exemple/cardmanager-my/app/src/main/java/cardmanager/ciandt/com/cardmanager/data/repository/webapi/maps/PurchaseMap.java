package cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps;

import com.google.gson.annotations.SerializedName;

/**
 * This class models a purchase entity.
 */
public class PurchaseMap {
    @SerializedName("date")
    public String date;

    @SerializedName("spent")
    public String spent;

    @SerializedName("value")
    public String value;
}