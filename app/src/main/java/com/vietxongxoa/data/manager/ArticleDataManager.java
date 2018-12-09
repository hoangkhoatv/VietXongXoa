package com.vietxongxoa.data.manager;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.ArticleCreateListener;
import com.vietxongxoa.data.listeners.DataListener;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.data.remote.ApiHelper;
import com.vietxongxoa.data.remote.ApiInterface;
import com.vietxongxoa.data.remote.ErrorUtils;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataResponse;
import com.vietxongxoa.model.Error;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ArticleDataManager extends BaseDataManager {

    @Inject
    ArticleDataManager(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
    }

    public void getData(final DataListener listener, int offset, int limit) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<List<Data<Article>>>> call = apiService.getListPost(
                mPreferencesHelper.getKeyToken(),
                String.valueOf(limit),
                String.valueOf(offset),
                "#trending"
        );
        call.enqueue(new Callback<DataResponse<List<Data<Article>>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<List<Data<Article>>>> call,
                    @NonNull Response<DataResponse<List<Data<Article>>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    Error error = ErrorUtils.parseError(response);
                    if (error != null) {
                        listener.onError(error.message);

                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<List<Data<Article>>>> call,
                    @NonNull Throwable t
            ) {
                listener.onError(t.getMessage());
            }
        });

    }

    public void postWrite(final ArticleCreateListener listener, JsonObject content) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Data<Article>>> call = apiService.postWirte(
                mPreferencesHelper.getKeyToken(),
                content
        );
        call.enqueue(new Callback<DataResponse<Data<Article>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Data<Article>>> call,
                    @NonNull Response<DataResponse<Data<Article>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    Error error = ErrorUtils.parseError(response);
                    if (error != null) {
                        listener.onError(error.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Data<Article>>> call,
                    @NonNull Throwable t
            ) {

            }
        });
    }

    public void getDetail(final ArticleCreateListener listener, String idPost) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Data<Article>>> call = apiService.getDetail(
                mPreferencesHelper.getKeyToken(),
                idPost
        );
        call.enqueue(new Callback<DataResponse<Data<Article>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Data<Article>>> call,
                    @NonNull Response<DataResponse<Data<Article>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    Error error = ErrorUtils.parseError(response);
                    if (error != null) {
                        listener.onError(error.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Data<Article>>> call,
                    @NonNull Throwable t
            ) {
                listener.onError(t.getMessage());
            }
        });
    }
}
