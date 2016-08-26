package cardmanager.ciandt.com.cardmanager.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class models a purchase entity.
 */
public class Purchase {
    public String date;
    public String spent;
    public String value;

    @Override
    public String toString() {
        return "User{" +
                "date='" + date + '\'' +
                ", spent='" + spent + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}