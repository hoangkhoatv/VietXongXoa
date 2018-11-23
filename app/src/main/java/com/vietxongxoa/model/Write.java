package com.vietxongxoa.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Write {
    @SerializedName("content")
    @Expose
    public String content;
    public static JsonObject getJson(String content){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("content",content);
        return jsonObject;
    }
}
