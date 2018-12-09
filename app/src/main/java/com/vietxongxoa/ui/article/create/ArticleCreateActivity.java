package com.vietxongxoa.ui.article.create;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.NewArticle;
import com.vietxongxoa.ui.article.detail.ArticleArticleDetailActivity;
import com.vietxongxoa.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ArticleCreateActivity extends BaseActivity implements ArticleCreateMvpView {

    @BindView(R.id.edtWrite)
    EditText editWrite;

    @Inject
    ArticleCreatePresenter<ArticleCreateMvpView> mWritePresenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ArticleCreateActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_article_create);
        setActionBar();
        ButterKnife.bind(this);
        ButterKnife.bind(this);
        mWritePresenter.attachView(this);
    }

    @Override
    public void showData(Data<Article> data) {
        if (data != null) {
            Intent intent = ArticleArticleDetailActivity.getStartIntent(getBaseContext());
            intent.putExtra(PreferencesHelper.KEY_ID, data.uuid);
            intent.putExtra(PreferencesHelper.KEY_CONTENT, data.attributes.content);
            intent.putExtra(PreferencesHelper.KEY_AUTHOR, data.attributes.author);
            intent.putExtra(PreferencesHelper.KEY_DATE, data.attributes.created);
            this.startActivity(intent);
            BaseActivity.hideKeyboard(this);
            finish();
        }
    }

    @Override
    public void showError(String error) {

    }

    @OnClick(R.id.btnDelete)
    public void onDeleteWrite(View view) {
        if (!editWrite.getText().toString().matches("")) {
            mWritePresenter.postData(
                    NewArticle.getJson(String.valueOf(editWrite.getText()))
            );

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
                finish();
            }
        });
        TextView txtTitle = (TextView) view.findViewById(R.id.text_title);
        txtTitle.setText(getString(R.string.title_detail));
    }
}
