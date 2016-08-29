package cardmanager.ciandt.com.cardmanager.data.repository.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cardmanager.ciandt.com.cardmanager.data.model.User;

/**
 * Created by root on 18/08/16.
 */

public class SharedPreferencesRepository {
    private SharedPreferences mSharedPreferences;
    public SharedPreferencesRepository(Context context, final String storageName) {
        mSharedPreferences = context.getSharedPreferences(storageName, Context.MODE_PRIVATE);
    }


    public <T> void insertOrUpdate(String key, T user)
    {
        SharedPreferences.Editor prefsEditor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public <T> T get(String key, Class<T> clazz)
    {
        if (contains(key)) {
            Gson gson = new Gson();
            String json = mSharedPreferences.getString(key, "");
            return gson.fromJson(json, clazz);
        }

        return null;
    }

    public <T> ArrayList<T> getList(String key, Class<T[]> clazz) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(mSharedPreferences.getString(key, null), clazz);
        if (array != null)
            return new ArrayList<>(Arrays.asList(array));
        return null;
    }

    public void delete(String key) {
        if (contains(key)) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }
}
