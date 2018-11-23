package com.vietxongxoa.ui.create;

import com.google.gson.JsonObject;
import com.vietxongxoa.model.Users;
import com.vietxongxoa.ui.base.MvpPresenter;

public interface CreateMvpPresenter <V extends CreateMvpView> extends MvpPresenter<V> {

    void postData(JsonObject username);
    Users getUserName();
    void setUsersName(Users users);

}
