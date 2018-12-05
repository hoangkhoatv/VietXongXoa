package com.vietxongxoa.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.BaseItem;
import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.adapter.CommentAdapter;
import com.vietxongxoa.ui.adapter.PostAdapter;
import com.vietxongxoa.ui.base.BaseActivity;
import com.vietxongxoa.ui.create.CreateMvpView;
import com.vietxongxoa.ui.main.ItemInteractiveListener;
import com.vietxongxoa.ui.main.MainActivity;
import com.vietxongxoa.ui.viewholder.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailMvpView, CommentAdapter.ItemClickListener, CommentAdapter.RetryLoadMoreListener, ItemInteractiveListener {

    private CommentAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.editCommnent)
    TextView edtComment;
    @BindView(R.id.btnComment)
    ImageButton btnComment;


    @Inject
    DetailPresenter<DetailMvpView> mDetailPresenter;
    Data<PostItem> dataTupe;
    int limit = 10;
    int currentPage  = 0;
    int endPage = -1;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_detail);
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

    public void getData(){
        Intent intent = getIntent();
        dataTupe = new Data<PostItem>();
        dataTupe.attributes = new PostItem();
        dataTupe.uuid = intent.getStringExtra(PreferencesHelper.KEY_ID);
        dataTupe.attributes.author = intent.getStringExtra(PreferencesHelper.KEY_AUTHOR);
        dataTupe.attributes.content = intent.getStringExtra(PreferencesHelper.KEY_CONTENT);
        dataTupe.attributes.created = intent.getStringExtra(PreferencesHelper.KEY_DATE);
        dataTupe.attributes.love = intent.getStringExtra(PreferencesHelper.KEY_NUM_LOVE);
        dataTupe.attributes.loved = intent.getBooleanExtra(PreferencesHelper.KEY_LOVED,false);
        dataTupe.attributes.comment = intent.getIntExtra(PreferencesHelper.KEY_COMMET,0);
        dataTupe.attributes.type = BaseItem.HEADER_TYPE;
    }

    @Override
    public void showData(Data<PostItem> data) {
        final List<Object> baseItems = new ArrayList<>();
        data.attributes.type = BaseItem.HEADER_TYPE;
        baseItems.add(data);
        DetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()){
                    adapter.clear();
                    swipeRefreshLayout .setRefreshing(false);
                }
                if (baseItems.size()  != 0){
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
    public void showDataCommets(List<Data<CommentItem>> commens) {

        final List<Object> baseItems = new ArrayList<>();
        for (int i = 0; i < commens.size(); i++){
            commens.get(i).attributes.type = BaseItem.SECOND_TYPE;
            baseItems.add(commens.get(i));
        }
        DetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()){
                    adapter.clear();
                    swipeRefreshLayout .setRefreshing(false);
                }
                if (baseItems.size()  != 0){
                    adapter.add(baseItems);
                } else {
                    adapter.onHidden();
                }
            }
        });
    }

    @Override
    public void showErrorCommets(String error) {

    }

    @Override
    public void showPostComment(Data<CommentItem> comment) {
        final List<Object> baseItems = new ArrayList<>();
        comment.attributes.type = BaseItem.SECOND_TYPE;
        baseItems.add(comment);
        DetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (baseItems.size()  != 0){
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
        if (status.matches("success")){
            adapter.loved(position);
        }
    }

    @Override
    public void showUnlove(String status, int position) {
        if (status.matches("success")){
            adapter.unLove(position);
        }
    }


    @Override
    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View view = getLayoutInflater().inflate(R.layout.action_bar_back_layout,
                null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView txtTitle = (TextView) view.findViewById(R.id.text_title);
        txtTitle.setText(getString(R.string.title_detail));
    }

    private void setupRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(this, this, this,this);
        recyclerView.setAdapter(adapter);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager){
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
                if(adapter!=null){
                    endlessRecyclerViewScrollListener.resetState();
                    currentPage = 0;
                    mDetailPresenter.getData(dataTupe.uuid);
                }
            }
        });
        List<Object> baseItem = new ArrayList<>();
        baseItem.add(dataTupe);
        adapter.add(baseItem);

    }

    @Override
    public void onLove(String idPost, boolean isLove, int position) {
        if(!isLove){
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

    private void loadMore(final int page){
        adapter.startLoadMore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(page == endPage){
                    adapter.onReachEnd();
                    return;
                }
                mDetailPresenter.getComment(dataTupe.uuid, limit, page);
            }
        }, 500);
    }

    @OnClick(R.id.btnComment)
    public void onClickComment(View view) {
        if (!edtComment.getText().toString().matches("")) {
            btnComment.setEnabled(false);
            BaseActivity.hideKeyboard(this);
            mDetailPresenter.postComment(dataTupe.uuid, edtComment.getText().toString());
        }
    }
}
