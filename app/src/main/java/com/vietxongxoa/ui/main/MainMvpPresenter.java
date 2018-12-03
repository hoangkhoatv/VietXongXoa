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

import com.vietxongxoa.ui.base.MvpPresenter;


public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void getData(int page, int limit);

    void postLove(String uuid, int position);

    void deleteLove(String uuid, int position);

}
