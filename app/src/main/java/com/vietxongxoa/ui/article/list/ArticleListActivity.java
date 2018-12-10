package com.vietxongxoa.ui.article.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vietxongxoa.R;
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
        showDialogRate();
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
            public void onLoadMore(final int mOffset) {
                offset = mOffset;
                loadMore(mOffset);
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
                            offset = 0;
                            loadMore(offset);
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

    private void loadMore(final int offset) {
        adapter.startLoadMore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (offset == endPage) {
                    adapter.onReachEnd();
                    return;
                }
                mMainPresenter.getData(limit, offset);
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
}
