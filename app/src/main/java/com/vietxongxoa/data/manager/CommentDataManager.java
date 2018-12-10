package com.vietxongxoa.data.manager;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.CommentListener;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.data.remote.ApiHelper;
import com.vietxongxoa.data.remote.ApiInterface;
import com.vietxongxoa.data.remote.ErrorUtils;
import com.vietxongxoa.model.Comment;
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
public class CommentDataManager extends BaseDataManager {

    @Inject
    CommentDataManager(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
    }

    public void getComments(final CommentListener listener, String uuid, int limit, int offset) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<List<Data<Comment>>>> call = apiService.getComments(
                mPreferencesHelper.getToken(),
                uuid,
                limit,
                offset);
        call.enqueue(new Callback<DataResponse<List<Data<Comment>>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<List<Data<Comment>>>> call,
                    @NonNull Response<DataResponse<List<Data<Comment>>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    if (response.body().data != null){
                        listener.onResponse(response.body().data);
                    }

                } else {
                    Error error = ErrorUtils.parseError(response);
                    if (error != null) {
                        listener.onError(error.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<List<Data<Comment>>>> call,
                    @NonNull Throwable t
            ) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void postComment(final CommentListener listener, JsonObject content) {

        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Data<Comment>>> call = apiService.postComment(
                mPreferencesHelper.getToken(),
                content
        );
        call.enqueue(new Callback<DataResponse<Data<Comment>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Data<Comment>>> call,
                    @NonNull Response<DataResponse<Data<Comment>>> response
            ) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().status.matches("success")) {
                        listener.onCommentResponse(response.body().data);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Data<Comment>>> call,
                    @NonNull Throwable t
            ) {

            }
        });
    }
}
