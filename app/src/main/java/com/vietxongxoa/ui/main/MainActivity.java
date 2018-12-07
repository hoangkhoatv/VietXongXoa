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

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.BaseItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.model.WriteItem;
import com.vietxongxoa.ui.adapter.PostAdapter;
import com.vietxongxoa.ui.base.BaseActivity;
import com.vietxongxoa.ui.viewholder.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements MainMvpView,
        PostAdapter.ItemClickListener,
        PostAdapter.RetryLoadMoreListener,
        ItemInteractiveListener {

    @Inject
    MainPresenter<MainMvpView> mMainPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    int limit = 10;
    int currentPage = 0;
    int endPage = -1;
    private PostAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private boolean isWrite = true;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
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
    public void showData(final List<Data<PostItem>> data) {
        final List<Object> baseItems = new ArrayList<>();
        for (int i = 0; i < data.size() - 1; i++) {
            data.get(i).attributes.type = BaseItem.SECOND_TYPE;
            baseItems.add(data.get(i));
        }
        MainActivity.this.runOnUiThread(new Runnable() {
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

    protected void showDialogRate() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_dialog))
                .setMessage(getString(R.string.content_dialog))
                .setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            String CHPlayUrl = "http://play.google.com/store/apps/details?id=";
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(CHPlayUrl + getBaseContext().getPackageName())));
                        }
                    }
                })
                .setNegativeButton(getString(R.string.no_didalog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
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
            Data<WriteItem> dataItem = new Data<WriteItem>();
            dataItem.attributes = new WriteItem();
            dataItem.attributes.type = BaseItem.HEADER_TYPE;

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
            public void onLoadMore(final int page) {
                currentPage = page;
                loadMore(page);
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (adapter != null) {
                    isWrite = true;
                    endlessRecyclerViewScrollListener.resetState();
                    currentPage = 0;
                    loadMore(currentPage);

                }
            }
        });
    }

    private void updateAdapter() {
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onRetryLoadMore() {
        loadMore(currentPage);

    }

    private void loadMore(final int page) {
        adapter.startLoadMore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page == endPage) {
                    adapter.onReachEnd();
                    return;
                }
                mMainPresenter.getData(limit, page);
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
