package com.vietxongxoa.injection.component;

import android.app.Application;
import android.content.Context;

import com.vietxongxoa.MyApplication;
import com.vietxongxoa.data.manager.ArticleDataManager;
import com.vietxongxoa.data.manager.CommentDataManager;
import com.vietxongxoa.data.manager.FirebaseDataManager;
import com.vietxongxoa.data.manager.LoveDataManager;
import com.vietxongxoa.data.manager.UserDataManager;
import com.vietxongxoa.injection.annotation.ApplicationContext;
import com.vietxongxoa.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MyApplication myApplication);

    @ApplicationContext
    Context context();

    Application application();

    ArticleDataManager articleDataManager();
    CommentDataManager commentDataManager();
    LoveDataManager loveDataManager();
    UserDataManager userDataManager();
    FirebaseDataManager  firebaseDataManager();
}
