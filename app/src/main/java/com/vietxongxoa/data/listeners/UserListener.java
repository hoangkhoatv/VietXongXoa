package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.User;

public interface UserListener {

    void onResponse(User user);

    void onError(String error);
}
