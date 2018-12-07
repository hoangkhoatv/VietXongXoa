package com.vietxongxoa.ui.create;

import com.vietxongxoa.model.Users;
import com.vietxongxoa.ui.base.MvpView;

public interface CreateMvpView extends MvpView {

    void showData(Users data);

    void showError(String error);
}
