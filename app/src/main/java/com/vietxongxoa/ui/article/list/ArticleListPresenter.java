package com.vietxongxoa.ui.article.list;

import com.google.gson.JsonObject;
import com.vietxongxoa.data.listeners.DataListener;
import com.vietxongxoa.data.listeners.FirebaseListener;
import com.vietxongxoa.data.listeners.LoveListener;
import com.vietxongxoa.data.manager.ArticleDataManager;
import com.vietxongxoa.data.manager.FirebaseDataManager;
import com.vietxongxoa.data.manager.LoveDataManager;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.DataResponse;
import com.vietxongxoa.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import static com.vietxongxoa.data.local.PreferencesHelper.KEY_FCM_TOKEN;


public class ArticleListPresenter<V extends ArticleListMvpView> extends BasePresenter<V> implements ArticleListMvpPresenter<V> {

    private final ArticleDataManager articleDataManager;
    private final LoveDataManager loveDataManager;
    private final FirebaseDataManager firebaseDataManager;


    @Inject
    ArticleListPresenter(
            ArticleDataManager articleDataManager,
            LoveDataManager loveDataManager,
            FirebaseDataManager firebaseDataManager) {
        this.articleDataManager = articleDataManager;
        this.loveDataManager = loveDataManager;
        this.firebaseDataManager = firebaseDataManager;
    }

    @Override
    public void getData(int limit, int page) {

        articleDataManager.getData(new DataListener() {
            @Override
            public void onResponse(List<Data<Article>> data) {
                getMvpView().showData(data);
            }
            @Override
            public void onError(String error) {
                if (getMvpView() != null) {
                    getMvpView().showError(error);
                }
            }
        }, page, limit);
    }

    @Override
    public void postLove(String uuid, final int position) {
        JsonObject content = new JsonObject();
        content.addProperty("article_uuid", uuid);

        loveDataManager.postLove(new LoveListener() {
            @Override
            public void onLoved(String status) {
                getMvpView().showLove(status, position);
            }

            @Override
            public void onUnLove(String status) {

            }

            @Override
            public void onError(String error) {
                getMvpView().showErrorLove(error);
            }
        }, content);
    }

    @Override
    public void deleteLove(String uuid, final int position) {
        JsonObject content = new JsonObject();
        content.addProperty("article_uuid", uuid);
        loveDataManager.deleteLove(new LoveListener() {
            @Override
            public void onLoved(String status) {
            }

            @Override
            public void onUnLove(String status) {
                getMvpView().showUnLove(status, position);
            }

            @Override
            public void onError(String error) {
                getMvpView().showErrorLove(error);
            }
        }, content);
    }

    @Override
    public void postFirebaseToken(final String token) {
        JsonObject content = new JsonObject();
        content.addProperty(KEY_FCM_TOKEN , token);
        firebaseDataManager.postFirebaseToken(new FirebaseListener() {
            @Override
            public void onFirebaseResponse(DataResponse<Boolean> dataResponse) {
                assert getMvpView() != null;
                if (dataResponse.data){
                    getMvpView().showFirebaseReponse(dataResponse.status);
                }
            }

            @Override
            public void onFirebasError(String error) {
                getMvpView().showFirebaseError(error);

            }
        },content);


    }
}
