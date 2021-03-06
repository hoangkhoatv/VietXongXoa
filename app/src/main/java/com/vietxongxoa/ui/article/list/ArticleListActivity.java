package com.vietxongxoa.ui.article.list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.ArticleCreateModel;
import com.vietxongxoa.model.BaseModel;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.ui.adapter.PostAdapter;
import com.vietxongxoa.ui.base.BaseActivity;
import com.vietxongxoa.ui.viewholder.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListActivity extends BaseActivity
        implements ArticleListMvpView,
        PostAdapter.ItemClickListener,
        PostAdapter.RetryLoadMoreListener,
        ItemInteractiveListener {

    public static final int UPDATE_ITEM = 1000;

    @Inject
    ArticleListPresenter<ArticleListMvpView> mMainPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    int limit = 10;
    int offset = 0;
    int endPage = -1;
    private PostAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private boolean isWrite = true;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ArticleListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_article_list);
        setActionBar();
        ButterKnife.bind(this);
        mMainPresenter.attachView(this);
        setupRecyclerView();
        getFirebaseToken();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();

    }

    @Override
    public void showData(final List<Data<Article>> data) {
        final List<Object> baseItems = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).attributes.type = BaseModel.SECOND_TYPE;
            baseItems.add(data.get(i));
        }
        ArticleListActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()) {
                    adapter.clear();
                    swipeRefreshLayout.setRefreshing(false);
                }
                setWritePost();
                if (baseItems.size() != 0) {
                    adapter.add(baseItems);
                } else {
                    adapter.onHidden();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            showDialogRate();
        } else {
            recyclerView.scrollToPosition(0);
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void showError(String error) {
        if (adapter != null) {
            adapter.onLoadMoreFailed();
        }

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void showLove(String status, int position) {
        if (status.matches("success")) {
            adapter.loved(position);
        }

    }

    @Override
    public void showUnLove(String status, int position) {
        if (status.matches("success")) {
            adapter.unLove(position);
        }

    }

    @Override
    public void showErrorLove(String error) {

    }

    @Override
    public void showFirebaseReponse(String fcmDeviceToken) {

    }

    @Override
    public void showFirebaseError(String error) {

    }

    private void getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        PreferencesHelper preferencesHelper = new PreferencesHelper(getBaseContext());
                        String oldDeviceToken = preferencesHelper.getKeyFcmToken();
                        if (oldDeviceToken == null || !oldDeviceToken.equals(token)) {
                            mMainPresenter.postFirebaseToken(token);
                        }
                    }
                }
        );
    }

    private void setWritePost() {
        if (isWrite) {
            Data<ArticleCreateModel> dataItem = new Data<ArticleCreateModel>();
            dataItem.attributes = new ArticleCreateModel();
            dataItem.attributes.type = BaseModel.HEADER_TYPE;

            List<Object> baseItem = new ArrayList<>();
            baseItem.add(dataItem);
            adapter.add(baseItem);
            isWrite = false;
        }

    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(this, this, this, this);
        recyclerView.setAdapter(adapter);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(final int offset) {
                ArticleListActivity.this.offset = offset;
                loadMore(offset);
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (adapter != null) {
                            isWrite = true;
                            endlessRecyclerViewScrollListener.resetState();
                            endlessRecyclerViewScrollListener.onLoadMore(0);
                        }
                    }
                }
        );

    }

    private void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onRetryLoadMore() {
        loadMore(offset);

    }

    private void loadMore(final int mOffset) {
        adapter.startLoadMore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (offset == endPage) {
                    adapter.onReachEnd();
                    return;
                }
                mMainPresenter.getData(limit, mOffset);
            }
        }, 500);

    }

    @Override
    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.action_bar_layout, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
        );
        actionBar.setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        TextView txtTitle = (TextView) view.findViewById(R.id.text_title);
        txtTitle.setText(getString(R.string.title_main));
    }

    @Override
    public void onLove(String idPost, boolean isLove, int position) {
        if (!isLove) {
            mMainPresenter.postLove(idPost, position);
        } else {
            mMainPresenter.deleteLove(idPost, position);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dataResult) {
        if (requestCode == UPDATE_ITEM && resultCode == Activity.RESULT_OK) {
            Bundle extras = dataResult.getExtras();
            Article article = new Article();
            int position = extras.getInt(PreferencesHelper.KEY_PLACE, 0);
            boolean loved = dataResult.getBooleanExtra(PreferencesHelper.KEY_LOVED, false);
            String numlove = dataResult.getStringExtra(PreferencesHelper.KEY_NUM_LOVE);
            int comments = dataResult.getIntExtra(PreferencesHelper.KEY_COMMENT, 0);
            article.love = numlove;
            article.loved = loved;
            article.comment = comments;
            adapter.changeItem(position, article);
        }
    }
}
