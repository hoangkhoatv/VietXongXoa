package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.Comment;
import com.vietxongxoa.model.Data;

import java.util.List;

public interface CommentListener {

    void onResponse(List<Data<Comment>> dataResponse);

    void onError(String error);

    void onCommentResponse(Data<Comment> dataResponse);

    void onCommentError(String error);
}
