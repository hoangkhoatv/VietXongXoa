package com.vietxongxoa.data.manager;

import com.vietxongxoa.data.local.PreferencesHelper;

class BaseDataManager {

    PreferencesHelper mPreferencesHelper;
    BaseDataManager(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }
}
