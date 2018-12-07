package com.vietxongxoa.ui.detail;

import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.MvpView;

import java.util.List;

public interface DetailMvpView extends MvpView {

    void showData(Data<PostItem> data);

    void showError(String error);

    void showDataComments(List<Data<CommentItem>> commens);

    void showErrorComments(String error);

    void showPostComment(Data<CommentItem> comment);

    void showErrorPostComment(String error);

    void showLove(String status, int position);

    void showUnLove(String status, int position);
}
