package com.vietxongxoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataResponse<T> {
    @SerializedName("data")
    @Expose
    public T data;

    @SerializedName("total")
    @Expose
    public String total;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    @Expose
    public String message;
}
