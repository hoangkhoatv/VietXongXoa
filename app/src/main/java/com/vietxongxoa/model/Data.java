package com.vietxongxoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data<V> {
    @SerializedName("attributes")
    @Expose
    public V attributes;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("relationships")
    @Expose
    public Relationships relationships;

    @SerializedName("uuid")
    @Expose
    public String uuid;

    @SerializedName("message")
    @Expose
    public String message;
}
