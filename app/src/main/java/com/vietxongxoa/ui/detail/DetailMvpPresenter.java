package com.vietxongxoa.ui.detail;

import com.vietxongxoa.ui.base.MvpPresenter;

public interface DetailMvpPresenter <V extends DetailMvpView> extends MvpPresenter<V> {

    void getData(String idPost);

    void getComment(String uuid, int limit, int offset);

    void postComment(String uuid, String content);

    void postLove(String uuid, int position);

    void deleteLove(String uuid, int position);
}

