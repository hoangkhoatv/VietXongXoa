package com.vietxongxoa.data.listeners;

public interface LoveListener {

    void onLoved(String status);

    void onUnLove(String status);

    void onError(String error);
}
