package com.vietxongxoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataReponse<T> {
    @SerializedName("data")
    @Expose
    public T data;

    @SerializedName("total")
    @Expose
    public String total;

    @SerializedName("status")
    @Expose
    public String status;


}
