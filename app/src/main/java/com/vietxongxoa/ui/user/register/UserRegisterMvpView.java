package com.vietxongxoa.ui.user.register;

import com.vietxongxoa.model.User;
import com.vietxongxoa.ui.base.MvpView;

public interface UserRegisterMvpView extends MvpView {

    void showData(User data);

    void showError(String error);
}
