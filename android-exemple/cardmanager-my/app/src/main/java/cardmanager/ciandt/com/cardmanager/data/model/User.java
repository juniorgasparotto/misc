package cardmanager.ciandt.com.cardmanager.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a user entity.
 */
public class User {
    public String name;
    public String profileImagePath;
    public ArrayList<Card> cards;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", profileImagePath='" + profileImagePath + '\'' +
                '}';
    }

}