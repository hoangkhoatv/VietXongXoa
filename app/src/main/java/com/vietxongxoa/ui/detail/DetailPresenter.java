package com.vietxongxoa.ui.detail;

import android.os.Handler;

import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.data.listeners.CommentListener;
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
                getMvpView().showDataCommets(dataReponse);
            }

            @Override
            public void onError(String error) {
                getMvpView().showError(error);

            }
        }, uuid, limit, offset);
    }

}
