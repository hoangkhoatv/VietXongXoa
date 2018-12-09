package com.vietxongxoa.injection.component;

import com.vietxongxoa.injection.annotation.PerActivity;
import com.vietxongxoa.injection.module.ActivityModule;
import com.vietxongxoa.ui.article.detail.ArticleDetailActivity;
import com.vietxongxoa.ui.user.register.UserRegisterActivity;
import com.vietxongxoa.ui.article.list.ArticleListActivity;
import com.vietxongxoa.ui.article.create.ArticleCreateActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(ArticleListActivity activity);

    void inject(UserRegisterActivity activity);

    void inject(ArticleCreateActivity activity);

    void inject(ArticleDetailActivity activity);
}
