package com.yisinian.news.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yisinian.news.R;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.views.BaseView;

/**
 * Created by deng on 2015/8/28.
 * Description:base activity
 */
public class BaseActivity extends AppCompatActivity implements BaseView {

    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    protected int layoutResID = R.layout.activity_base;

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        NewsLog.i(TAG, "--->onCreate()");
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNewError() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }
}
