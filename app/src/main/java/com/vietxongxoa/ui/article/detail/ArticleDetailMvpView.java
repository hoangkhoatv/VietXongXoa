package com.vietxongxoa.ui.article.detail;

import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.Comment;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.ui.base.MvpView;

import java.util.List;

public interface ArticleDetailMvpView extends MvpView {

    void showData(Data<Article> data);

    void showError(String error);

    void showDataComments(List<Data<Comment>> commens);

    void showErrorComments(String error);

    void showPostComment(Data<Comment> comment);

    void showErrorPostComment(String error);

    void showLove(String status, int position);

    void showUnLove(String status, int position);
}
