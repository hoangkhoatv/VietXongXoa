package com.vietxongxoa.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.vietxongxoa.MyApplication;
import com.vietxongxoa.R;
import com.vietxongxoa.injection.component.ActivityComponent;
import com.vietxongxoa.injection.component.DaggerActivityComponent;
import com.vietxongxoa.injection.module.ActivityModule;
import com.vietxongxoa.utils.DialogUtils;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private ActivityComponent mActivityComponent;
    private ProgressDialog progressDialog;
    protected static final int TIME_INTERVAL = 1500;
    protected long mBackPressed;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, new_user a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MyApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        progressDialog = DialogUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void showDialogRate() {
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
                .setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    public abstract void setActionBar();
}
