package com.vietxongxoa.ui.detail;

import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showData(PostItem data);

    void showError(String error);
}
