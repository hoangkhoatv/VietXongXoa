package com.vietxongxoa.data;

import android.support.annotation.NonNull;
import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.CommentListener;
import com.vietxongxoa.data.listeners.CreateListener;
import com.vietxongxoa.data.listeners.DataListener;
import com.vietxongxoa.data.listeners.LoveListener;
import com.vietxongxoa.data.listeners.WriteListener;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.data.remote.ApiHelper;
import com.vietxongxoa.data.remote.ApiInterface;
import com.vietxongxoa.data.remote.ErrorUtils;
import com.vietxongxoa.model.Error;
import com.vietxongxoa.model.Comment;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataResponse;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.User;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class DataManager {

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }

    public User getUserName() {
        User user = null;
        final String data = mPreferencesHelper.getData(PreferencesHelper.KEY_USER);
        if (data != null) {
            user = new User();
            user.username = data;

        }
        return user;
    }

    public void setUSerName(User user) {
        mPreferencesHelper.putData(PreferencesHelper.KEY_USER, user.username);
        mPreferencesHelper.putData(PreferencesHelper.KEY_TOKEN, user.token);
    }

    public void postWrite(final WriteListener listener, JsonObject content) {
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

    public void postCreateUser(final CreateListener listener, JsonObject username) {
        final String data = mPreferencesHelper.getData(PreferencesHelper.KEY_USER);
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

    public void getDetail(final WriteListener listener, String idPost) {
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

    public void postLove(final LoveListener listener, JsonObject content) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Boolean>> call = apiService.postLove(
                mPreferencesHelper.getKeyToken(),
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
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Boolean>> call,
                    @NonNull Throwable t
            ) {

            }
        });
    }

    public void deleteLove(final LoveListener listener, JsonObject content) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Boolean>> call = apiService.deleteLove(
                mPreferencesHelper.getKeyToken(),
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
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataResponse<Boolean>> call, @NonNull Throwable t) {

            }
        });

    }

    public void getComments(final CommentListener listener, String uuid, int limit, int offset) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<List<Data<Comment>>>> call = apiService.getComments(
                mPreferencesHelper.getKeyToken(),
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
                mPreferencesHelper.getKeyToken(),
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
