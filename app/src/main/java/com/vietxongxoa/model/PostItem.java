package com.vietxongxoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostItem extends BaseItem {

        @SerializedName("author")
        @Expose
        public String author;

        @SerializedName("created")
        @Expose
        public String created;

        @SerializedName("content")
        @Expose
        public String content;

        @SerializedName("loved")
        @Expose
        public boolean loved;

        @SerializedName("love")
        @Expose
        public String love;

}
