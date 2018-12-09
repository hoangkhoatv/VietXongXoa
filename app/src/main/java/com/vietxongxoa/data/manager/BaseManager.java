package com.vietxongxoa.data.manager;

import com.vietxongxoa.data.local.PreferencesHelper;

class BaseManager {

    PreferencesHelper mPreferencesHelper;
    BaseManager(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }
}
