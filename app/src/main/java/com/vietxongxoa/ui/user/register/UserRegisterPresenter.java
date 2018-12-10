package com.vietxongxoa.ui.user.register;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.UserListener;
import com.vietxongxoa.data.manager.UserDataManager;
import com.vietxongxoa.model.User;
import com.vietxongxoa.ui.base.BasePresenter;

import javax.inject.Inject;

public class UserRegisterPresenter<V
        extends UserRegisterMvpView>
        extends BasePresenter<V>
        implements UserRegisterMvpPresenter<V> {

    private final UserDataManager userDataManager;

    @Inject
    UserRegisterPresenter(UserDataManager dataManager) {
        this.userDataManager = dataManager;
    }

    @Override
    public void postData(JsonObject username) {
        getMvpView().showLoading();
        userDataManager.postCreateUser(
                new UserListener() {
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
        return userDataManager.getUserName();
    }

    @Override
    public void setUsersName(User user) {
        userDataManager.setUSerName(user);
    }
}
