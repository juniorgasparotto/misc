package cardmanager.ciandt.com.cardmanager.data.repository.webapi.maps;

import com.google.gson.annotations.SerializedName;

/**
 * This class models a user entity.
 */
public class UserMap {
    @SerializedName("name")
    public String name;

    @SerializedName("image_profile")
    public String profileImagePath;
}