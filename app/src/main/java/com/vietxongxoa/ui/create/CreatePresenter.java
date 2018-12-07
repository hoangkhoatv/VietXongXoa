package com.vietxongxoa.ui.create;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.data.listeners.CreateListener;
import com.vietxongxoa.model.Users;
import com.vietxongxoa.ui.base.BasePresenter;

import javax.inject.Inject;

public class CreatePresenter<V extends CreateMvpView> extends BasePresenter<V> implements CreateMvpPresenter<V> {
    private final DataManager mDataManager;

    @Inject
    public CreatePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void postData(JsonObject username) {
        getMvpView().showLoading();
        mDataManager.postCreateUser(new CreateListener() {
            @Override
            public void onResponse(Users users) {
                getMvpView().hideLoading();
                getMvpView().showData(users);
            }

            @Override
            public void onError(String error) {
                getMvpView().hideLoading();
                getMvpView().showError(error);
            }
        }, username);
    }

    @Override
    public Users getUserName() {
        return mDataManager.getUserName();
    }

    @Override
    public void setUsersName(Users users) {
        mDataManager.setUSerName(users);
    }
}
