package com.vietxongxoa.ui.write;

import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.MvpView;

import java.util.List;

public interface WriteMvpView extends MvpView {

    void showData(Data<PostItem> data);

    void showError(String error);
}
