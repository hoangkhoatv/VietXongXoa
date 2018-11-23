package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;


public interface WriteListener {
    void onResponse(Data<PostItem> dataReponse);

    void onError(String error);
}
