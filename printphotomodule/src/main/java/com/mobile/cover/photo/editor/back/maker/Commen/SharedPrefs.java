package com.mobile.cover.photo.editor.back.maker.Commen;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mobile.cover.photo.editor.back.maker.model.Address;

import java.util.ArrayList;
import java.util.Arrays;

//SharedPreferences manager class
public class SharedPrefs {

    public static final String URL_INDEX = "URL_INDEX";
    public static final String AD_INDEX = "ad_index";
    public static final String SELLER = "seller";
    public static final String TOKEN = "token";

    public static final String AccessToken = "AccessToken";
    // save facebool profile id
    public static final String ProfileId = "ProfileId";
    public static final String CART_COUNT = "CART_COUNT";
    public static final String REVIEW = "review";
    //here you can centralize all your shared prefs keys
    public static final String ITEM_SIZE = "ITEM_SIZE";
    public static final String isapproved = "isapproved";
    public static final String uid = "uid";
    public static final String mobile = "mobile";
    public static final String Sellerid = "sellerid";
    public static final String SPLASH_AD_DATA = "splash_ad_data";
    public static final String COUNT = "more_app_count";
    public static final String SCREEN_HEIGHT = "screen_height";
    public static final String SCREEN_WIDTH = "screen_width";
    public static String currency_id = "currency_id";
    public static String country_id = "country_id";
    public static String country_name = "country_name";
    public static String country_code = "country_code";
    public static String last_country_code = "last_country_code";
    public static String Set_Title = "Set_Title";
    public static String title_category = "title_category";
    public static String FULL_AD_IMAGE = "FULL_AD_IMAGE";
    public static String Database_Creation = "DB_FLAG";
    public static String MORE_APP_INDEX = "more_app_index";

    //    public static String WATCHED_JSON_ARRAY="json_array";
    public static String LANGUAGE = "language";
    public static String videoplay = "0";
    //TODO: Update SharedPref file name
    //SharedPreferences file name
    private static String SHARED_PREFS_FILE_NAME = "Phone_case_shared_prefs";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean contain(Context context, String key) {
        return getPrefs(context).contains(key);
    }

    public static void clearPrefs(Context context) {
        getPrefs(context).edit().clear().commit();
    }

    //Save Booleans
    public static void savePref(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).commit();
    }

    //Get Booleans
    public static boolean getBoolean(Context context, String key) {
        return getPrefs(context).getBoolean(key, false);
    }

    //Get Booleans if not found return a predefined default value
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    //Strings
    public static void save(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key) {
        return getPrefs(context).getString(key, "");
    }

    public static void save(Context context, String key, Address address) {

        Gson gson = new Gson();
        String json = gson.toJson(address);
        getPrefs(context).edit().putString(key, json).commit();
    }

    public static Address getAddress(Context context, String key) {
        Gson gson = new Gson();
        String json = getPrefs(context).getString(key, "");
        return gson.fromJson(json, Address.class);
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    //Integers
    public static void save(Context context, String key, int value) {
        getPrefs(context).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key) {
        return getPrefs(context).getInt(key, 0);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getPrefs(context).getInt(key, defaultValue);
    }

    //Floats
    public static void save(Context context, String key, float value) {
        getPrefs(context).edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context context, String key) {
        return getPrefs(context).getFloat(key, 0);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        return getPrefs(context).getFloat(key, defaultValue);
    }

    //Longs
    public static void save(Context context, String key, long value) {
        getPrefs(context).edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key) {
        return getPrefs(context).getLong(key, 0);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getPrefs(context).getLong(key, defaultValue);
    }

    public static ArrayList<String> getListString(Context context, String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(getPrefs(context).getString(key, ""), "‚‗‚")));
    }

    public static ArrayList<Address> getAdListAddress(Context context, String key) {
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(context, key);
        ArrayList<Address> objects = new ArrayList<Address>();
        for (String jObjString : objStrings) {
            Address value = gson.fromJson(jObjString, Address.class);
            objects.add(value);
        }
        return objects;
    }


    public static void putAdListAddress(Context context, String key, ArrayList<Address> objArray) {
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for (Address obj : objArray) {
            objStrings.add(gson.toJson(obj));
        }
        putListString(context, key, objStrings);
    }

    public static void putListString(Context context, String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        getPrefs(context).edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    public static void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

}