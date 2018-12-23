package com.vietxongxoa.data.manager;

import com.vietxongxoa.data.local.PreferencesHelper;

class BaseDataManager {

    PreferencesHelper mPreferencesHelper;
    static  final String SUCCESS = "success";
    BaseDataManager(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }
}
