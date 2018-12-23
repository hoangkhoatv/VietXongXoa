package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataResponse;

import java.util.List;

public interface DataListener {

    void onResponse(List<Data<Article>> dataReponse);

    void onError(String error);

}
