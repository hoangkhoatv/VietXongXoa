package com.vietxongxoa.data.manager;

import android.util.Log;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.FirebaseListener;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.data.remote.ApiHelper;
import com.vietxongxoa.data.remote.ApiInterface;
import com.vietxongxoa.data.remote.ErrorUtils;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataResponse;
import com.vietxongxoa.model.Error;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vietxongxoa.data.local.PreferencesHelper.KEY_FCM_TOKEN;

public class FirebaseDataManager extends BaseDataManager {

    @Inject
    FirebaseDataManager(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
    }

    public void postFirebaseToken(final FirebaseListener listener, final JsonObject content){
        ApiInterface apiService = ApiHelper.getClient().create(ApiInterface.class);
        Call<DataResponse<Boolean>> call = apiService.postFirebaseToken(
                mPreferencesHelper.getToken(),
                content
        );
        call.enqueue(new Callback<DataResponse<Boolean>>() {
            @Override
            public void onResponse(Call<DataResponse<Boolean>> call, Response<DataResponse<Boolean>> response) {
                if (response.isSuccessful()){

                    if(response.body().status.matches(BaseDataManager.SUCCESS)){
                        listener.onFirebaseResponse(response.body());
                        mPreferencesHelper.setKeyFcmToken(
                                String.valueOf(
                                        content.getAsJsonObject().getAsJsonPrimitive(KEY_FCM_TOKEN).getAsString()
                                )
                        );
                    }
                }else {
                    Error error = ErrorUtils.parseError(response);
                    if (error != null) {
                        listener.onFirebasError(error.message);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse<Boolean>> call, Throwable t) {
                listener.onFirebasError(t.getMessage());
            }
        });
    }

}
