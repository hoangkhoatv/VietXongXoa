package com.vietxongxoa.ui.article.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.BaseModel;
import com.vietxongxoa.model.Comment;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.ui.adapter.CommentAdapter;
import com.vietxongxoa.ui.article.list.ItemInteractiveListener;
import com.vietxongxoa.ui.base.BaseActivity;
import com.vietxongxoa.ui.viewholder.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleDetailActivity
        extends BaseActivity
        implements ArticleDetailMvpView,
        CommentAdapter.ItemClickListener,
        CommentAdapter.RetryLoadMoreListener,
        ItemInteractiveListener {

    private CommentAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.editComment)
    TextView edtComment;
    @BindView(R.id.btnComment)
    ImageButton btnComment;

    private boolean isLoading = false;


    @Inject
    ArticleDetailPresenter<ArticleDetailMvpView> mDetailPresenter;
    Data<Article> data;
    int limit = 10;
    int currentPage = 0;
    int endPage = -1;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ArticleDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_article_detail);
        setActionBar();
        ButterKnife.bind(this);
        mDetailPresenter.attachView(this);
        getData();
        setupRecyclerView();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailPresenter.detachView();

    }

    public void getData() {
        Intent intent = getIntent();
        data = new Data<Article>();
        data.attributes = new Article();
        data.uuid = intent.getStringExtra(PreferencesHelper.KEY_ARTICLE_UUID);
        data.attributes.author = intent.getStringExtra(PreferencesHelper.KEY_AUTHOR);
        data.attributes.content = intent.getStringExtra(PreferencesHelper.KEY_CONTENT);
        data.attributes.created = intent.getStringExtra(PreferencesHelper.KEY_DATE);
        data.attributes.love = intent.getStringExtra(PreferencesHelper.KEY_NUM_LOVE);
        data.attributes.loved = intent.getBooleanExtra(PreferencesHelper.KEY_LOVED, false);
        data.attributes.comment = intent.getIntExtra(PreferencesHelper.KEY_COMMENT, 0);
        data.attributes.type = BaseModel.HEADER_TYPE;
    }

    @Override
    public void showData(Data<Article> data) {
        final List<Object> baseItems = new ArrayList<>();
        data.attributes.type = BaseModel.HEADER_TYPE;
        baseItems.add(data);
        ArticleDetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()) {
                    adapter.clear();
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (baseItems.size() != 0) {
                    adapter.add(baseItems);
                } else {
                    adapter.onHidden();
                }
                loadMore(currentPage);
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showDataComments(List<Data<Comment>> commens) {

        final List<Object> baseItems = new ArrayList<>();
        for (int i = 0; i < commens.size(); i++) {
            commens.get(i).attributes.type = BaseModel.SECOND_TYPE;
            baseItems.add(commens.get(i));
        }
        ArticleDetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()) {
                    adapter.clear();
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (baseItems.size() != 0) {
                    adapter.add(baseItems);
                } else {
                    adapter.onHidden();
                }
                isLoading = false;
            }
        });
    }

    @Override
    public void showErrorComments(String error) {

    }

    @Override
    public void showPostComment(Data<Comment> comment) {
        final List<Object> baseItems = new ArrayList<>();
        comment.attributes.type = BaseModel.SECOND_TYPE;
        baseItems.add(comment);
        ArticleDetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (baseItems.size() != 0) {
                    adapter.add(baseItems);
                } else {
                    adapter.onHidden();
                }
            }
        });
        btnComment.setEnabled(true);
        edtComment.setText("");
        adapter.increaseComment();
        recyclerView.scrollToPosition(adapter.sizeData());
    }

    @Override
    public void showErrorPostComment(String error) {

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
    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.action_bar_layout_with_back_btn, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
        );
        actionBar.setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataResult();

            }
        });
        TextView txtTitle = (TextView) view.findViewById(R.id.text_title);
        txtTitle.setText(getString(R.string.title_comment));
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(this, this, this, this);
        recyclerView.setAdapter(adapter);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(final int offset) {
                currentPage = offset;
                loadMore(offset);
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (adapter != null) {
                    if (isLoading == false){
                        endlessRecyclerViewScrollListener.resetState();
                        currentPage = 0;
                        mDetailPresenter.getData(data.uuid);
                        isLoading = true;
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }
            }
        });
        List<Object> baseItem = new ArrayList<>();
        baseItem.add(data);
        adapter.add(baseItem);
    }

    @Override
    public void onLove(String idPost, boolean isLove, int position) {
        if (!isLove) {
            mDetailPresenter.postLove(idPost, position);
        } else {
            mDetailPresenter.deleteLove(idPost, position);
        }
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
                isLoading = true;
                mDetailPresenter.getComment(data.uuid, limit, page);

            }
        }, 500);
    }

    @OnClick(R.id.btnComment)
    public void onClickComment(View view) {
        if (!edtComment.getText().toString().matches("")) {
            btnComment.setEnabled(false);
            BaseActivity.hideKeyboard(this);
            mDetailPresenter.postComment(data.uuid, edtComment.getText().toString());
        }
    }

    protected void sendDataResult(){
        Intent intentChange = new Intent();
        intentChange.putExtra( PreferencesHelper.KEY_PLACE,
                getIntent().getIntExtra(PreferencesHelper.KEY_POSITON,0));
        Data<Article> articleData =  (Data<Article>) adapter.getItem(0);
        intentChange.putExtra(PreferencesHelper.KEY_LOVED, articleData.attributes.loved);
        intentChange.putExtra(PreferencesHelper.KEY_NUM_LOVE, articleData.attributes.love);
        intentChange.putExtra(PreferencesHelper.KEY_COMMENT, articleData.attributes.comment);
        setResult(Activity.RESULT_OK, intentChange);
        finish();
    }

    @Override
    public void onBackPressed() {
       sendDataResult();
    }
}
