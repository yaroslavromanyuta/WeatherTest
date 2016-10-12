package yaroslavromanyuta.com.ua.weathertest.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.fragments.BaseFragment;

/**
 * Created by Yaroslav on 12.10.2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected boolean isLoading;
    private ProgressDialog progressDialog;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissWaitingDialog();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        attachActivityViews();
        initActivityViews();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void attachActivityViews() {
    }

    protected void initActivityViews() {
    }

    public void showWaitingDialog(String waitingMessage, boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setCancelable(cancelable);
        if (waitingMessage != null) {
            progressDialog.setMessage(waitingMessage);
        }
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void showWaitingDialog(String waitingMessage) {
        showWaitingDialog(waitingMessage, false);
    }

    public void showWaitingDialog() {
        showWaitingDialog("");
    }

    public void showAlertDialog(String message, DialogInterface.OnClickListener onButtonClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.allert_dialog_ok_button_text), onButtonClickListener)
                .create();
        alertDialog.show();
    }

    public void dismissWaitingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = this.getCurrentFocus();
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            v.clearFocus();
        }
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (!onBackWasPressed())
            super.onBackPressed();
    }

    public boolean onBackWasPressed() {
        boolean wasHandled = false;
        BaseFragment baseFragment = getVisibleFragment();
        if (baseFragment != null) {
            wasHandled = baseFragment.onBackWasPressed();
        }
        return wasHandled;
    }

    public BaseFragment getVisibleFragment() {
        FragmentManager fragmentManager = BaseActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible() && fragment instanceof BaseFragment)
                    return (BaseFragment) fragment;
            }
        }
        return null;
    }

    public void onBackItemClick() {

    }

    public void changeFragment(int layout, Fragment fragment, boolean addToBackStack) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(layout, fragment);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            fragmentTransaction.commit();
        }
    }

    protected void requestPermissions(String ... permissions){
        RxPermissions.getInstance(this)
                .requestEach(permissions)
                .subscribe(this::catchPermissionResult);
    }

    protected abstract void catchPermissionResult(Permission permission);
}
