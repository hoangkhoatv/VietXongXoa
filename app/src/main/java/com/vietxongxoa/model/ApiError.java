package com.vietxongxoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiError {
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    @Expose
    public String message;
}
