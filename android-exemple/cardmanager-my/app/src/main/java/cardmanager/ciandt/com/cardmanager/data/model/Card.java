package cardmanager.ciandt.com.cardmanager.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a card entity.
 */
public class Card {
    public String number;
    public String flagImagePath;
    public String cardImagePath;
    public String color;
    public String dueDate;
    public String limitAvailable;
    public String invoiceAmount;
    public ArrayList<Purchase> purchases;

    @Override
    public String toString() {
        return "User{" +
                "number='" + number + '\'' +
                ", flagImagePath='" + flagImagePath + '\'' +
                ", cardImagePath='" + cardImagePath + '\'' +
                ", color='" + color + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", limitAvailable='" + limitAvailable + '\'' +
                ", invoiceAmount='" + invoiceAmount + '\'' +
                '}';
    }
}