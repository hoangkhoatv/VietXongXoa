package com.vietxongxoa.ui.write;

import com.google.gson.JsonObject;
import com.vietxongxoa.ui.base.MvpPresenter;

public interface WriteMvpPresenter<V extends WriteMvpView> extends MvpPresenter<V> {
    void postData(JsonObject content);
}
