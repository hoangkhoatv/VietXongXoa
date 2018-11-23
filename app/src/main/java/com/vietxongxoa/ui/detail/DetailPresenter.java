package com.vietxongxoa.ui.detail;

import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.data.listeners.WriteListener;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.BasePresenter;

import javax.inject.Inject;

public class DetailPresenter <V extends DetailMvpView> extends BasePresenter<V> implements DetailMvpPresenter<V> {
    private final DataManager mDataManager;

    @Inject
    public DetailPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getData(String idPost) {
        getMvpView().showLoading();
        mDataManager.getDetail(new WriteListener() {
            @Override
            public void onResponse(Data<PostItem> dataReponse) {
                getMvpView().hideLoading();
                getMvpView().showData(dataReponse.attributes);
            }

            @Override
            public void onError(String error) {
                getMvpView().hideLoading();
                getMvpView().showError(error);
            }
        },idPost);
    }
}
