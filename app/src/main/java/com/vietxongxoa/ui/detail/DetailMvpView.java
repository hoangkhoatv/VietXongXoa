package com.vietxongxoa.ui.detail;

import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.MvpView;

import java.util.List;

public interface DetailMvpView extends MvpView {

    void showData(Data<PostItem> data);

    void showError(String error);

    void showDataCommets(List<Data<CommentItem>> commens);

    void showErrorCommets(String error);

}
