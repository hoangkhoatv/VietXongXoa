package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.Data;

public interface ArticleListener {

    void onResponse(Data<Article> dataResponse);

    void onError(String error);
}
