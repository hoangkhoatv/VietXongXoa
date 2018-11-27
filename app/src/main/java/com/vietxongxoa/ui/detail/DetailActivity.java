package com.vietxongxoa.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.base.BaseActivity;
import com.vietxongxoa.ui.create.CreateMvpView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements DetailMvpView {

    @BindView(R.id.text_name)
    TextView textName;

    @BindView(R.id.text_post)
    TextView textPost;
    @BindView(R.id.text_date)
    TextView textDate;



    @Inject
    DetailPresenter<DetailMvpView> mDetailPresenter;

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

    }
    public void getData(){
        Intent intent = getIntent();
        String idPost = intent.getStringExtra(PreferencesHelper.KEY_ID);
        String author = intent.getStringExtra(PreferencesHelper.KEY_AUTHOR);
        String content = intent.getStringExtra(PreferencesHelper.KEY_CONTENT);
        String date = intent.getStringExtra(PreferencesHelper.KEY_DATE);
        textName.setText(author);
        textPost.setText(content);
        textDate.setText(date);

    }

    @Override
    public void showData(PostItem data) {

    }

    @Override
    public void showError(String error) {

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
}
