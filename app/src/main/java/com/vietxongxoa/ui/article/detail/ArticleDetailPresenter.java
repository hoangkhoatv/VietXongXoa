package com.vietxongxoa.ui.article.detail;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.ArticleListener;
import com.vietxongxoa.data.listeners.CommentListener;
import com.vietxongxoa.data.listeners.LoveListener;
import com.vietxongxoa.data.manager.ArticleDataManager;
import com.vietxongxoa.data.manager.CommentDataManager;
import com.vietxongxoa.data.manager.LoveDataManager;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.Comment;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class ArticleDetailPresenter<V extends ArticleDetailMvpView> extends BasePresenter<V> implements ArticleDetailMvpPresenter<V> {

    private final CommentDataManager commentDataManager;
    private final ArticleDataManager articleDataManager;
    private final LoveDataManager loveDataManager;

    @Inject
    ArticleDetailPresenter(
            CommentDataManager commentDataManager,
            ArticleDataManager articleDataManager,
            LoveDataManager loveDataManager
    ) {
        this.commentDataManager = commentDataManager;
        this.articleDataManager = articleDataManager;
        this.loveDataManager = loveDataManager;
    }

    @Override
    public void getData(String idPost) {
        articleDataManager.getDetail(new ArticleListener() {
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
        commentDataManager.getComments(new CommentListener() {
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
        commentDataManager.postComment(new CommentListener() {
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
