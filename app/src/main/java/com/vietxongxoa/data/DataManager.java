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

package com.vietxongxoa.data;

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
import com.vietxongxoa.model.DataReponse;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.model.Users;

import com.google.gson.JsonObject;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.http.Body;


@Singleton
public class DataManager {

    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public DataManager(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        this.mPreferencesHelper = preferencesHelper;
        this.mApiHelper = apiHelper;
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
        ApiInterface apiService = mApiHelper.getCient().create(ApiInterface.class);
        Call<DataReponse<Data<PostItem>>> call = apiService.postWirte(
                mPreferencesHelper.getKeyToken(),
                content
        );
        call.enqueue(new Callback<DataReponse<Data<PostItem>>>() {
            @Override
            public void onResponse(Call<DataReponse<Data<PostItem>>> call, Response<DataReponse<Data<PostItem>>> response) {
                if (response.isSuccessful() && response.body().status.toString().matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if(apiError!=null){
                        listener.onError(apiError.message);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataReponse<Data<PostItem>>> call, Throwable t) {

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

        ApiInterface apiService = mApiHelper.getCient().create(ApiInterface.class);
        Call<DataReponse<Data<Users>>> call = apiService.postCreateUser(username);
        call.enqueue(new Callback<DataReponse<Data<Users>>>() {
            @Override
            public void onResponse(Call<DataReponse<Data<Users>>> call, Response<DataReponse<Data<Users>>> response) {
                if (response.isSuccessful() && response.body().status.toString().matches("success")) {
                    listener.onResponse(response.body().data.attributes);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if(apiError!=null){
                        listener.onError(apiError.message);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataReponse<Data<Users>>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void getDetail(final WriteListener listener, String idPost){
        ApiInterface apiService = mApiHelper.getCient().create(ApiInterface.class);
        Call<DataReponse<Data<PostItem>>> call = apiService.getDetail(idPost);
        call.enqueue(new Callback<DataReponse<Data<PostItem>>>() {
            @Override
            public void onResponse(Call<DataReponse<Data<PostItem>>> call, Response<DataReponse<Data<PostItem>>> response) {
                if (response.isSuccessful() && response.body().status.toString().matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if(apiError!=null){
                        listener.onError(apiError.message);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataReponse<Data<PostItem>>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void getData(final DataListener listener, int offset, int limit) {
//
//        final String data = mPreferencesHelper.getData();
//
//        if (data != null) {
//            listener.onResponse(data);
//            return;
//        }
        ApiInterface apiService = mApiHelper.getCient().create(ApiInterface.class);
        Call<DataReponse<List<Data<PostItem>>>> call = apiService.getListPost(
                mPreferencesHelper.getKeyToken(),
                String.valueOf(limit),
                String.valueOf(offset),
                "#trending"
        );
        call.enqueue(new Callback<DataReponse<List<Data<PostItem>>>>() {
            @Override
            public void onResponse(Call<DataReponse<List<Data<PostItem>>>> call, Response<DataReponse<List<Data<PostItem>>>> response) {
                if (response.isSuccessful() && response.body().status.toString().matches("success")) {
                    listener.onResponse(response.body().data);
                } else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    if(apiError!=null){
                        listener.onError(apiError.message);

                    }
                }
            }

            @Override
            public void onFailure(Call<DataReponse<List<Data<PostItem>>>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });

    }

    public void postLove(final LoveListener listener, JsonObject content){
        ApiInterface apiService = mApiHelper.getCient().create(ApiInterface.class);
        Call<DataReponse<Boolean>> call = apiService.postLove(
                mPreferencesHelper.getKeyToken(),
                content
        );
        call.enqueue(new Callback<DataReponse<Boolean>>() {
            @Override
            public void onResponse(Call<DataReponse<Boolean>> call, Response<DataReponse<Boolean>> response) {
                if (response.isSuccessful()) {
                    listener.onLoved(response.body().status.toString());
                }
            }

            @Override
            public void onFailure(Call<DataReponse<Boolean>> call, Throwable t) {

            }
        });
    }

    public  void deleteLove(final  LoveListener listener, JsonObject content){
        ApiInterface apiService = mApiHelper.getCient().create(ApiInterface.class);
        Call<DataReponse<Boolean>> call = apiService.deleteLove(
                mPreferencesHelper.getKeyToken(),
                content
        );
        call.enqueue(new Callback<DataReponse<Boolean>>() {
            @Override
            public void onResponse(Call<DataReponse<Boolean>> call, Response<DataReponse<Boolean>> response) {
                if (response.isSuccessful()) {
                    listener.onUnlove(response.body().status.toString());
                }
            }

            @Override
            public void onFailure(Call<DataReponse<Boolean>> call, Throwable t) {

            }
        });

    }

    public void getComments(final CommentListener listener, String uuid , int limit, int offset){
        ApiInterface apiService = mApiHelper.getCient().create(ApiInterface.class);
        Call<DataReponse<List<Data<CommentItem>>>> call = apiService.getComments(
                uuid,
                limit,
                offset);
       call.enqueue(new Callback<DataReponse<List<Data<CommentItem>>>>() {
           @Override
           public void onResponse(Call<DataReponse<List<Data<CommentItem>>>> call, Response<DataReponse<List<Data<CommentItem>>>> response) {
               if (response.isSuccessful() && response.body().status.toString().matches("success")) {
                   listener.onResponse(response.body().data);
               } else {
                   ApiError apiError = ErrorUtils.parseError(response);
                   if(apiError!=null){
                       listener.onError(apiError.message);
                   }
               }
           }

           @Override
           public void onFailure(Call<DataReponse<List<Data<CommentItem>>>> call, Throwable t) {
               listener.onError(t.getMessage());
           }
       });
    }
}
