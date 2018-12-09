package com.vietxongxoa.ui.article.detail;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.data.listeners.CommentListener;
import com.vietxongxoa.data.listeners.LoveListener;
import com.vietxongxoa.data.listeners.WriteListener;
import com.vietxongxoa.model.Comment;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class ArticleDetailPresenter<V extends ArticleDetailMvpView> extends BasePresenter<V> implements ArticleDetailMvpPresenter<V> {
    private final DataManager mDataManager;

    @Inject
    public ArticleDetailPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getData(String idPost) {
        mDataManager.getDetail(new WriteListener() {
            @Override
            public void onResponse(Data<Article> dataResponse) {
                getMvpView().showData(dataResponse);
            }

            @Override
            public void onError(String error) {
                getMvpView().showError(error);
            }
        }, idPost);
    }

    @Override
    public void getComment(String uuid, int limit, int offset) {
        mDataManager.getComments(new CommentListener() {
            @Override
            public void onResponse(List<Data<Comment>> dataResponse) {
                getMvpView().showDataComments(dataResponse);
            }

            @Override
            public void onError(String error) {
                getMvpView().showError(error);

            }

            @Override
            public void onCommentResponse(Data<Comment> dataResponse) {
            }

            @Override
            public void onCommentError(String error) {

            }
        }, uuid, limit, offset);
    }

    @Override
    public void postComment(String uuid, String content) {
        JsonObject json = new JsonObject();
        json.addProperty("article_uuid", uuid);
        json.addProperty("content", content);
        mDataManager.postComment(new CommentListener() {
            @Override
            public void onResponse(List<Data<Comment>> dataResponse) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onCommentResponse(Data<Comment> dataResponse) {
                getMvpView().showPostComment(dataResponse);
            }

            @Override
            public void onCommentError(String error) {
                getMvpView().showError(error);
            }
        }, json);


    }

    @Override
    public void postLove(String uuid, final int position) {
        JsonObject content = new JsonObject();
        content.addProperty("article_uuid", uuid);

        mDataManager.postLove(new LoveListener() {
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
        mDataManager.deleteLove(new LoveListener() {
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
