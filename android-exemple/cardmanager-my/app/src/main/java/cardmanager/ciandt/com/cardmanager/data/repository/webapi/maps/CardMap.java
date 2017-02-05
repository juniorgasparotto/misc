package cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class models a card entity.
 */
public class CardMap {
    @SerializedName("card_number")
    public String number;

    @SerializedName("image_flag")
    public String flagImagePath;

    @SerializedName("image_card")
    public String cardImagePath;

    @SerializedName("color_card")
    public String color;

    @SerializedName("due_date")
    public String dueDate;

    @SerializedName("limit_available")
    public String limitAvailable;

    @SerializedName("invoice_amount")
    public String invoiceAmount;

    @SerializedName("extract")
    public List<PurchaseMap> purchases;
}