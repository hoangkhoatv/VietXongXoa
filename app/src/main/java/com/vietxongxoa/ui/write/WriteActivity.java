package com.vietxongxoa.ui.write;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.model.Write;
import com.vietxongxoa.ui.base.BaseActivity;
import com.vietxongxoa.ui.detail.DetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WriteActivity extends BaseActivity implements WriteMvpView {

    @BindView(R.id.edtWrite)
    EditText editWrite;

    @Inject
    WritePresenter<WriteMvpView> mWritePresenter;
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, WriteActivity.class);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_write);
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

        ButterKnife.bind(this);
        ButterKnife.bind(this);
        mWritePresenter.attachView(this);
    }

    @Override
    public void showData(Data<PostItem> data) {
        if(data!=null){
            Intent intent = DetailActivity.getStartIntent(getBaseContext());
            intent.putExtra(PreferencesHelper.KEY_ID,data.id);
            intent.putExtra(PreferencesHelper.KEY_CONTENT,data.attributes.content);
            intent.putExtra(PreferencesHelper.KEY_AUTHOR,data.attributes.author);
            intent.putExtra(PreferencesHelper.KEY_DATE,data.attributes.created);
            this.startActivity(intent);
            BaseActivity.hideKeyboard(this);
            finish();
        }
    }

    @Override
    public void showError(String error) {

    }

    @OnClick(R.id.btnDelelte)
    public void onDeleteWrite(View view) {
        if (!editWrite.getText().toString().matches("")){
            mWritePresenter.postData(
                    Write.getJson(String.valueOf(editWrite.getText()))
            );

        }
    }

}
