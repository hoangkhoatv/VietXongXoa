package com.vietxongxoa.data.manager;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.UserListener;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.data.remote.ApiHelper;
import com.vietxongxoa.data.remote.ApiInterface;
import com.vietxongxoa.data.remote.ErrorUtils;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataResponse;
import com.vietxongxoa.model.Error;
import com.vietxongxoa.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class UserDataManager extends BaseDataManager {

    @Inject
    UserDataManager(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
    }

    public User getUserName() {
        User user = null;
        final String data = mPreferencesHelper.getUserName();
        if (data != null) {
            user = new User();
            user.username = data;
        }
        return user;
    }

    public void setUSerName(User user) {
        mPreferencesHelper.putData(PreferencesHelper.KEY_USERNAME, user.username);
        mPreferencesHelper.putData(PreferencesHelper.KEY_TOKEN, user.token);
    }

    public void postCreateUser(final UserListener listener, JsonObject username) {
        final String data = mPreferencesHelper.getUserName();
        if (data != null) {
            User user = new User();
            user.username = data;
            listener.onResponse(user);
            return;
        }
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Data<User>>> call = apiService.postCreateUser(username);
        call.enqueue(new Callback<DataResponse<Data<User>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Data<User>>> call,
                    @NonNull Response<DataResponse<Data<User>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data.attributes);
                } else {
                    Error error = ErrorUtils.parseError(response);
                    if (error != null) {
                        listener.onError(error.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Data<User>>> call,
                    @NonNull Throwable t
            ) {
                listener.onError(t.getMessage());
            }
        });
    }
}
