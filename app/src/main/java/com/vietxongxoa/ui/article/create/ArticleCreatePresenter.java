package com.vietxongxoa.ui.article.create;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.data.listeners.WriteListener;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.ui.base.BasePresenter;

import javax.inject.Inject;

public class ArticleCreatePresenter<V extends ArticleCreateMvpView> extends BasePresenter<V> implements ArticleCreateMvpPresenter<V> {

    private final DataManager mDataManager;

    @Inject
    public ArticleCreatePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void postData(JsonObject content) {
        getMvpView().showLoading();
        mDataManager.postWrite(new WriteListener() {
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

