package cardmanager.ciandt.com.cardmanager.data.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class models a card entity.
 */
public class Payment {
    public String name;
    public String id;
    public String barCode;
    public Date date;

    @Override
    public String toString() {
        return
            "PaymentSchedule {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", barCode='" + barCode + '\'' +
            '}';
    }
}