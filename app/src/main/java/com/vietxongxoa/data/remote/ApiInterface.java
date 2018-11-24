package com.vietxongxoa.data.remote;

import android.support.annotation.RawRes;

import com.google.gson.JsonObject;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataReponse;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.model.Users;
import com.vietxongxoa.model.Write;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("articles")
    Call<DataReponse<List<Data<PostItem>>>> getListPost(
            @Header("Authorization") String authKey,
            @Query("limit") String limit,
            @Query("offset") String offset
    );

    @POST("users")
    Call<DataReponse<Data<Users>>> postCreateUser(
            @Body JsonObject username
    );

    @POST("articles")
    Call<DataReponse<Data<PostItem>>> postWirte(
            @Header("Authorization") String authKey,
            @Body JsonObject content
    );

    @GET("articles/{idPost}")
    Call<DataReponse<Data<PostItem>>> getDetail(
            @Path("idPost") String idPost
    );
}
