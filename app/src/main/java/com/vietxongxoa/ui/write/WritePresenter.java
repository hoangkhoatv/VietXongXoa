package com.vietxongxoa.ui.write;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.data.listeners.WriteListener;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.BasePresenter;

import javax.inject.Inject;

public class WritePresenter<V extends WriteMvpView> extends BasePresenter<V> implements WriteMvpPresenter<V> {

    private final DataManager mDataManager;

    @Inject
    public WritePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void postData(JsonObject content) {
        getMvpView().showLoading();
        mDataManager.postWrite(new WriteListener() {
            @Override
            public void onResponse(Data<PostItem> dataReponse) {
                getMvpView().hideLoading();
                getMvpView().showData(dataReponse);
            }

            @Override
            public void onError(String error) {
                getMvpView().hideLoading();
                getMvpView().showError(error);
            }
        }, content);
    }
}

