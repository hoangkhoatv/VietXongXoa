/*
 *    Copyright (C) 2018 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.vietxongxoa.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.vietxongxoa.injection.annotation.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by amitshekhar on 13/01/17.
 */
@Singleton
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "vietxongxoa";
    private final SharedPreferences mPref;
    public static final String KEY_USER = "nameuser";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ID = "id post";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_CONTENT = "content";

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putData(String key,String data) {
        mPref.edit().putString(key, data).apply();
    }

    public String getData(String key) {
        return mPref.getString(key, null);
    }

}
