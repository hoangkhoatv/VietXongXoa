package com.vietxongxoa.ui.detail;

import com.vietxongxoa.ui.base.MvpPresenter;

public interface DetailMvpPresenter <V extends DetailMvpView> extends MvpPresenter<V> {

    void getData(String idPost);


}

