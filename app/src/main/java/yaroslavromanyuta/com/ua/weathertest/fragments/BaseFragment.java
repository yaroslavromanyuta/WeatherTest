package yaroslavromanyuta.com.ua.weathertest.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import yaroslavromanyuta.com.ua.weathertest.activities.BaseActivity;

/**
 * Created by Yaroslav on 12.10.2016.
 */

public abstract class BaseFragment extends Fragment {
    protected static boolean requestIsAccessCamera;
    protected static boolean requestIsAccessGallery;

    public void setIsLoading(boolean isLoading) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).setLoading(isLoading);
        }
    }

    public boolean isLoading() {
        boolean isLoading = false;
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            isLoading = ((BaseActivity) activity).isLoading();
        }
        return isLoading;
    }

    public BaseActivity getBaseActivity() {
        BaseActivity bActivity = null;
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            bActivity = (BaseActivity) activity;
        }
        return bActivity;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard();
    }

    public void hideKeyboard() {
        BaseActivity bActivity = getBaseActivity();
        if (bActivity != null) {
            bActivity.hideKeyboard();
        }
    }

    public void showWaitingDialog(String waitingMessage, boolean cancelable) {
        BaseActivity bActivity = getBaseActivity();
        if (bActivity != null) {
            bActivity.showWaitingDialog(waitingMessage, cancelable);
        }
    }

    public void showWaitingDialog(String waitingMessage) {
        BaseActivity bActivity = getBaseActivity();
        if (bActivity != null) {
            bActivity.showWaitingDialog(waitingMessage);
        }
    }

    public void showWaitingDialog() {
        BaseActivity bActivity = getBaseActivity();
        if (bActivity != null) {
            bActivity.showWaitingDialog();
        }
    }

    public void dismissWaitingDialog() {
        BaseActivity bActivity = getBaseActivity();
        if (bActivity != null) {
            bActivity.dismissWaitingDialog();
        }
    }

    public boolean backItemItemClick() {
        return false;
    }

    public void onBackItemClick() {
        if (getBaseActivity() != null && !backItemItemClick())
            getBaseActivity().onBackItemClick();
    }

    public boolean onBackWasPressed() {
        return false;
    }

    protected void changeFragment(int layout, Fragment fragment, boolean addToBackStack) {
        getBaseActivity().changeFragment(layout, fragment, addToBackStack);
    }


    protected void setToolbarTitle(String toolbarTitle, boolean setBackArrow) {
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(toolbarTitle);
            actionBar.setDefaultDisplayHomeAsUpEnabled(setBackArrow);
            actionBar.setDisplayHomeAsUpEnabled(setBackArrow);
        }
    }
}
