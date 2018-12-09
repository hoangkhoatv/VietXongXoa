package com.vietxongxoa.ui.article.list;

import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.ui.base.MvpView;

import java.util.List;

public interface ArticleListMvpView extends MvpView {

    void showData(List<Data<Article>> data);

    void showError(String error);

    void showLove(String status, int position);

    void showUnLove(String status, int position);
}
