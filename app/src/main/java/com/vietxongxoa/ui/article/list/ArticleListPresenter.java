package com.vietxongxoa.ui.article.list;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.DataListener;
import com.vietxongxoa.data.listeners.LoveListener;
import com.vietxongxoa.data.manager.ArticleDataManager;
import com.vietxongxoa.data.manager.LoveDataManager;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;


public class ArticleListPresenter<V extends ArticleListMvpView> extends BasePresenter<V> implements ArticleListMvpPresenter<V> {

    private final ArticleDataManager articleDataManager;
    private final LoveDataManager loveDataManager;

    @Inject
    ArticleListPresenter(
            ArticleDataManager articleDataManager,
            LoveDataManager loveDataManager
    ) {
        this.articleDataManager = articleDataManager;
        this.loveDataManager = loveDataManager;
    }

    @Override
    public void getData(int limit, int page) {

        articleDataManager.getData(new DataListener() {
            @Override
            public void onResponse(List<Data<Article>> data) {
                getMvpView().showData(data);
            }
            @Override
            public void onError(String error) {
                getMvpView().showError(error);
            }
        }, page, limit);
    }

    @Override
    public void postLove(String uuid, final int position) {
        JsonObject content = new JsonObject();
        content.addProperty("article_uuid", uuid);

        loveDataManager.postLove(new LoveListener() {
            @Override
            public void onLoved(String status) {
                getMvpView().showLove(status, position);
            }

            @Override
            public void onUnLove(String status) {

            }
        }, content);
    }

    @Override
    public void deleteLove(String uuid, final int position) {
        JsonObject content = new JsonObject();
        content.addProperty("article_uuid", uuid);
        loveDataManager.deleteLove(new LoveListener() {
            @Override
            public void onLoved(String status) {
            }

            @Override
            public void onUnLove(String status) {
                getMvpView().showUnLove(status, position);
            }
        }, content);
    }
}
