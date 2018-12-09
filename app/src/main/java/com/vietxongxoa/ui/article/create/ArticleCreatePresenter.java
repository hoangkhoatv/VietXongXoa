package com.vietxongxoa.ui.article.create;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.manager.ArticleDataManager;
import com.vietxongxoa.data.listeners.ArticleListener;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.ui.base.BasePresenter;

import javax.inject.Inject;

public class ArticleCreatePresenter<V extends ArticleCreateMvpView> extends BasePresenter<V> implements ArticleCreateMvpPresenter<V> {

    private final ArticleDataManager articleDataManager;

    @Inject
    ArticleCreatePresenter(ArticleDataManager articleDataManager) {
        this.articleDataManager = articleDataManager;
    }

    @Override
    public void postData(JsonObject content) {
        getMvpView().showLoading();
        articleDataManager.postWrite(new ArticleListener() {
            @Override
            public void onResponse(Data<Article> dataResponse) {
                getMvpView().hideLoading();
                getMvpView().showData(dataResponse);
            }

            @Override
            public void onError(String error) {
                getMvpView().hideLoading();
                getMvpView().showError(error);
            }
        }, content);
    }
}
