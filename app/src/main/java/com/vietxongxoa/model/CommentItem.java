package com.vietxongxoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentItem extends BaseItem {
    @SerializedName("author")
    @Expose
    public String author;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("created")
    @Expose
    public String created;

    @SerializedName("like")
    @Expose
    public int like;
}
