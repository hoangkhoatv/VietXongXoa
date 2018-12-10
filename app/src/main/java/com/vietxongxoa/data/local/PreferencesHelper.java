package com.vietxongxoa.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.vietxongxoa.injection.annotation.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "vietxongxoa";
    private final SharedPreferences mPref;
    public static final String KEY_USERNAME = "username";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ARTICLE_UUID = "article_uuid";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_DATE = "date";
    public static final String KEY_NUM_LOVE = "number love";
    public static final String KEY_LOVED = "loved";
    public static final String KEY_COMMENT = "comment";

    @Inject
    PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putData(String key, String data) {
        mPref.edit().putString(key, data).apply();
    }

    public String getData(String key) {
        return mPref.getString(key, null);
    }

    public String getToken() {
        return "Bearer " + mPref.getString(KEY_TOKEN, null);
    }

    public String getUserName() {
        return mPref.getString(KEY_USERNAME, null);
    }
}
