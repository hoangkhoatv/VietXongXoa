package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.Article;

public interface ArticleListener {

    void onResponse(Data<Article> dataResponse);

    void onError(String error);
}
