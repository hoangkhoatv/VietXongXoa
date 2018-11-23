package com.vietxongxoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data<V>{

    @SerializedName("attributes")
    @Expose
    public V attributes;

    @SerializedName("type")
    @Expose
    public  String type;

    @SerializedName("id")
    @Expose
    public  String id;

    @SerializedName("message")
    @Expose
    public  String message;


}
