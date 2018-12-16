package com.vietxongxoa.data.manager;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.LoveListener;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.data.remote.ApiHelper;
import com.vietxongxoa.data.remote.ApiInterface;
import com.vietxongxoa.data.remote.ErrorUtils;
import com.vietxongxoa.model.DataResponse;
import com.vietxongxoa.model.Error;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class LoveDataManager extends BaseDataManager {

    @Inject
    LoveDataManager(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
    }

    public void postLove(final LoveListener listener, JsonObject content) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Boolean>> call = apiService.postLove(
                mPreferencesHelper.getToken(),
                content
        );
        call.enqueue(new Callback<DataResponse<Boolean>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Boolean>> call,
                    @NonNull Response<DataResponse<Boolean>> response
            ) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    listener.onLoved(response.body().status);
                } else {
                    Error error = ErrorUtils.parseError(response);
                    if (error != null) {
                        listener.onError(error.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Boolean>> call,
                    @NonNull Throwable t
            ) {
                listener.onError(t.getMessage());

            }
        });
    }

    public void deleteLove(final LoveListener listener, JsonObject content) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Boolean>> call = apiService.deleteLove(
                mPreferencesHelper.getToken(),
                content
        );
        call.enqueue(new Callback<DataResponse<Boolean>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Boolean>> call,
                    @NonNull Response<DataResponse<Boolean>> response
            ) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    listener.onUnLove(response.body().status);
                } else {
                    Error error = ErrorUtils.parseError(response);
                    if (error != null) {
                        listener.onError(error.message);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataResponse<Boolean>> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());

            }
        });

    }
}
