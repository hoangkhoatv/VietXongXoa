package com.vietxongxoa.data.remote;

import com.google.gson.JsonObject;
import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataResponse;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("articles")
    Call<DataResponse<List<Data<PostItem>>>> getListPost(
            @Header("Authorization") String authKey,
            @Query("limit") String limit,
            @Query("offset") String offset,
            @Query("hashtag") String hashtag
    );

    @POST("users")
    Call<DataResponse<Data<Users>>> postCreateUser(
            @Body JsonObject username
    );

    @POST("articles")
    Call<DataResponse<Data<PostItem>>> postWirte(
            @Header("Authorization") String authKey,
            @Body JsonObject content
    );

    @GET("articles/{idPost}")
    Call<DataResponse<Data<PostItem>>> getDetail(
            @Header("Authorization") String authKey,
            @Path("idPost") String idPost
    );

    @POST("love")
    Call<DataResponse<Boolean>> postLove(
            @Header("Authorization") String authKey,
            @Body JsonObject content
    );

    @HTTP(method = "DELETE", path = "love", hasBody = true)
    Call<DataResponse<Boolean>> deleteLove(
            @Header("Authorization") String authKey,
            @Body JsonObject content
    );

    @GET("comment")
    Call<DataResponse<List<Data<CommentItem>>>> getComments(
            @Header("Authorization") String authKey,
            @Query("article_uuid") String uuid,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @POST("comment")
    Call<DataResponse<Data<CommentItem>>> postComment(
            @Header("Authorization") String authKey,
            @Body JsonObject content
    );
}
