package com.vietxongxoa.ui.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vietxongxoa.R;
import com.vietxongxoa.model.Users;
import com.vietxongxoa.ui.base.BaseActivity;
import com.vietxongxoa.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateActivity extends BaseActivity implements CreateMvpView {
    @Inject
    CreatePresenter<CreateMvpView> mCreatePresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CreateActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_create);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View view = getLayoutInflater().inflate(R.layout.action_bar_layout,
                null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        ButterKnife.bind(this);
        mCreatePresenter.attachView(this);
        if(mCreatePresenter.getUserName()!=null){
            startActivity(MainActivity.getStartIntent(getBaseContext()));
            finish();
        }

    }


    @Override
    public void showData(Users data) {
        if(data!=null){
            mCreatePresenter.setUsersName(data);
            startActivity(MainActivity.getStartIntent(getBaseContext()));
            finish();
        }
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    @BindView(R.id.edtName)
    EditText edtName;

    @OnClick(R.id.btnCreate)
    public void onCreateUser(View view) {
        if(!edtName.getFreezesText()){
            mCreatePresenter.postData(Users.getJson(String.valueOf(edtName.getText())));
        } else {
            Toast.makeText(getApplicationContext(),getString(R.string.toast_emtry_name),Toast.LENGTH_SHORT).show();
        }
    }

}
