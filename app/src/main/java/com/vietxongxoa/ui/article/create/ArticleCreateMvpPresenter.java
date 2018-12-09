package com.vietxongxoa.ui.article.create;

import com.google.gson.JsonObject;
import com.vietxongxoa.ui.base.MvpPresenter;

public interface ArticleCreateMvpPresenter<V extends ArticleCreateMvpView> extends MvpPresenter<V> {
    void postData(JsonObject content);
}
