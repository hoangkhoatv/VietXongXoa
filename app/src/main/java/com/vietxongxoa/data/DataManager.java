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
import com.vietxongxoa.model.ApiError;
import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataResponse;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.model.Users;
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

    public Users getUserName() {
        Users users = null;
        final String data = mPreferencesHelper.getData(PreferencesHelper.KEY_USER);
        if (data != null) {
            users = new Users();
            users.username = data;

        }
        return users;
    }

    public void setUSerName(Users users) {
        mPreferencesHelper.putData(PreferencesHelper.KEY_USER, users.username);
        mPreferencesHelper.putData(PreferencesHelper.KEY_TOKEN, users.token);
    }

    public void postWrite(final WriteListener listener, JsonObject content) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Data<PostItem>>> call = apiService.postWirte(
                mPreferencesHelper.getKeyToken(),
                content
        );
        call.enqueue(new Callback<DataResponse<Data<PostItem>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Data<PostItem>>> call,
                    @NonNull Response<DataResponse<Data<PostItem>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if (apiError != null) {
                        listener.onError(apiError.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Data<PostItem>>> call,
                    @NonNull Throwable t
            ) {

            }
        });
    }

    public void postCreateUser(final CreateListener listener, JsonObject username) {
        final String data = mPreferencesHelper.getData(PreferencesHelper.KEY_USER);
        if (data != null) {
            Users users = new Users();
            users.username = data;
            listener.onResponse(users);
            return;
        }
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Data<Users>>> call = apiService.postCreateUser(username);
        call.enqueue(new Callback<DataResponse<Data<Users>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Data<Users>>> call,
                    @NonNull Response<DataResponse<Data<Users>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data.attributes);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if (apiError != null) {
                        listener.onError(apiError.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Data<Users>>> call,
                    @NonNull Throwable t
            ) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void getDetail(final WriteListener listener, String idPost) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Data<PostItem>>> call = apiService.getDetail(
                mPreferencesHelper.getKeyToken(),
                idPost
        );
        call.enqueue(new Callback<DataResponse<Data<PostItem>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Data<PostItem>>> call,
                    @NonNull Response<DataResponse<Data<PostItem>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if (apiError != null) {
                        listener.onError(apiError.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<Data<PostItem>>> call,
                    @NonNull Throwable t
            ) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void getData(final DataListener listener, int offset, int limit) {
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<List<Data<PostItem>>>> call = apiService.getListPost(
                mPreferencesHelper.getKeyToken(),
                String.valueOf(limit),
                String.valueOf(offset),
                "#trending"
        );
        call.enqueue(new Callback<DataResponse<List<Data<PostItem>>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<List<Data<PostItem>>>> call,
                    @NonNull Response<DataResponse<List<Data<PostItem>>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if (apiError != null) {
                        listener.onError(apiError.message);

                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<List<Data<PostItem>>>> call,
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
        Call<DataResponse<List<Data<CommentItem>>>> call = apiService.getComments(
                mPreferencesHelper.getKeyToken(),
                uuid,
                limit,
                offset);
        call.enqueue(new Callback<DataResponse<List<Data<CommentItem>>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<List<Data<CommentItem>>>> call,
                    @NonNull Response<DataResponse<List<Data<CommentItem>>>> response
            ) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().status.matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if (apiError != null) {
                        listener.onError(apiError.message);
                    }
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DataResponse<List<Data<CommentItem>>>> call,
                    @NonNull Throwable t
            ) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void postComment(final CommentListener listener, JsonObject content) {

        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Data<CommentItem>>> call = apiService.postComment(
                mPreferencesHelper.getKeyToken(),
                content
        );
        call.enqueue(new Callback<DataResponse<Data<CommentItem>>>() {
            @Override
            public void onResponse(
                    @NonNull Call<DataResponse<Data<CommentItem>>> call,
                    @NonNull Response<DataResponse<Data<CommentItem>>> response
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
                    @NonNull Call<DataResponse<Data<CommentItem>>> call,
                    @NonNull Throwable t
            ) {

            }
        });
    }
}
