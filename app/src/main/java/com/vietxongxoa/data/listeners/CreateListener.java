package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.Users;

public interface CreateListener {
    void onResponse(Users users);

    void onError(String error);
}
