package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;

import java.util.List;

public interface  CommentListener {

    void onResponse(List<Data<CommentItem>> dataReponse);

    void onError(String error);

    void onCommnetResponse(Data<CommentItem> dataReponse);

    void onCommnetError(String error);
}

