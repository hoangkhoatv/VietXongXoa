package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.User;

public interface CreateListener {

    void onResponse(User user);

    void onError(String error);
}
