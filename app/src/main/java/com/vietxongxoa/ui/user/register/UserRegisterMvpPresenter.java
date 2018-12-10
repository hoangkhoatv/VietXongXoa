package com.vietxongxoa.ui.user.register;

import com.google.gson.JsonObject;
import com.vietxongxoa.model.User;
import com.vietxongxoa.ui.base.MvpPresenter;

public interface UserRegisterMvpPresenter<V extends UserRegisterMvpView> extends MvpPresenter<V> {

    void postData(JsonObject username);

    User getUserName();

    void setUsersName(User user);

}
