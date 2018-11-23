package com.vietxongxoa.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("token")
    @Expose
    public String token;

    public static JsonObject getJson(String username){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username);
        return jsonObject;
    }
}
