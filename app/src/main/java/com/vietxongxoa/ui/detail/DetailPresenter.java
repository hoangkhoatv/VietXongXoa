package com.vietxongxoa.ui.detail;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.data.listeners.CommentListener;
import com.vietxongxoa.data.listeners.LoveListener;
import com.vietxongxoa.data.listeners.WriteListener;
import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class DetailPresenter<V extends DetailMvpView> extends BasePresenter<V> implements DetailMvpPresenter<V> {
    private final DataManager mDataManager;

    @Inject
    public DetailPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getData(String idPost) {
        mDataManager.getDetail(new WriteListener() {
            @Override
            public void onResponse(Data<PostItem> dataReponse) {
                getMvpView().showData(dataReponse);
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
            public void onResponse(List<Data<CommentItem>> dataReponse) {
                getMvpView().showDataComments(dataReponse);
            }

            @Override
            public void onError(String error) {
                getMvpView().showError(error);

            }

            @Override
            public void onCommentResponse(Data<CommentItem> dataReponse) {
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
            public void onResponse(List<Data<CommentItem>> dataReponse) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onCommentResponse(Data<CommentItem> dataReponse) {
                getMvpView().showPostComment(dataReponse);
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
