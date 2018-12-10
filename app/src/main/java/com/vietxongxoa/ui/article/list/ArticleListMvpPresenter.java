package com.vietxongxoa.ui.article.list;

import com.vietxongxoa.ui.base.MvpPresenter;

public interface ArticleListMvpPresenter<V extends ArticleListMvpView> extends MvpPresenter<V> {

    void getData(int page, int limit);

    void postLove(String uuid, int position);

    void deleteLove(String uuid, int position);
}
