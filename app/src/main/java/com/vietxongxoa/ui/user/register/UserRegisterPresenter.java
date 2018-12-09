package com.vietxongxoa.ui.user.register;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.data.listeners.CreateListener;
import com.vietxongxoa.model.User;
import com.vietxongxoa.ui.base.BasePresenter;

import javax.inject.Inject;

public class UserRegisterPresenter<V
        extends UserRegisterMvpView>
        extends BasePresenter<V>
        implements UserRegisterMvpPresenter<V> {

    private final DataManager mDataManager;

    @Inject
    UserRegisterPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void postData(JsonObject username) {
        getMvpView().showLoading();
        mDataManager.postCreateUser(
                new CreateListener() {
                    @Override
                    public void onResponse(User user) {
                        getMvpView().hideLoading();
                        getMvpView().showData(user);
                    }

                    @Override
                    public void onError(String error) {
                        getMvpView().hideLoading();
                        getMvpView().showError(error);
                    }
                }, username
        );
    }

    @Override
    public User getUserName() {
        return mDataManager.getUserName();
    }

    @Override
    public void setUsersName(User user) {
        mDataManager.setUSerName(user);
    }
}
