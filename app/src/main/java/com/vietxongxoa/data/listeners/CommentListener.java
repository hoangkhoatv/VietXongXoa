package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;

import java.util.List;

public interface CommentListener {

    void onResponse(List<Data<CommentItem>> dataResponse);

    void onError(String error);

    void onCommentResponse(Data<CommentItem> dataResponse);

    void onCommentError(String error);
}
