package com.vietxongxoa.ui.article.create;

import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.ui.base.MvpView;

public interface ArticleCreateMvpView extends MvpView {
    void showData(Data<Article> data);

    void showError(String error);
}
