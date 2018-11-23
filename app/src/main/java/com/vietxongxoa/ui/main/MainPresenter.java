/*
 *    Copyright (C) 2018 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.vietxongxoa.ui.main;

import com.vietxongxoa.data.listeners.DataListener;
import com.vietxongxoa.data.DataManager;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;


public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    private final DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getData(int limit, int page) {

//        getMvpView().showLoading();

        mDataManager.getData(new DataListener() {
            @Override
            public void onResponse(List<Data<PostItem>> data) {
//                getMvpView().hideLoading();
                getMvpView().showData(data);
            }

            @Override
            public void onError(String error) {
//                getMvpView().hideLoading();
                getMvpView().showError(error);
            }
        }, page, limit);
    }
}
