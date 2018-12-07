package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;

import java.util.List;

public interface DataListener {

    void onResponse(List<Data<PostItem>> dataReponse);

    void onError(String error);
}
