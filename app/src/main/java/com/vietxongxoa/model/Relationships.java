package com.vietxongxoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Relationships {
    @SerializedName("relationships")
    @Expose
    List<Comment> comments;

}
