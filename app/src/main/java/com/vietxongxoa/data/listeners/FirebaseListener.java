package com.vietxongxoa.data.listeners;

import com.vietxongxoa.model.DataResponse;

public interface FirebaseListener {

    void onFirebaseResponse(DataResponse<Boolean> dataResponse);

    void onFirebasError(String error);

}
