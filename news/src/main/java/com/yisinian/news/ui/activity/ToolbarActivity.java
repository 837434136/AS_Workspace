package com.yisinian.news.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yisinian.news.R;

/**
 * Created by demg on 2015/9/8.
 */
public abstract class ToolbarActivity extends AppCompatActivity{

    abstract protected int getLayoutResource();

    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        mAppBar = (AppBarLayout) findViewById(R.id.base_app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.base_toolbar);
        if (isWebView()){
            mToolbar.setTitleTextColor(getResources().getColor(R.color.activity_toolbar_title));
            mToolbar.setBackgroundResource(R.color.activity_main_color_text_click);
            mToolbar.setNavigationIcon(R.drawable.web_view_back_selector);

            //点击用户个人信息
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToolbarActivity.this.finish();
                }
            });
            setSupportActionBar(mToolbar);
        }else{
            setSupportActionBar(mToolbar);
        }

        if (mAppBar == null || mToolbar == null){
            throw  new IllegalStateException("no have toolbar");
        }

        if (canBack()){//该方法是判断ToolbarActivity的子类
            //Activity是否可以返回上一级界面，如果可以返回的话就可以设置相关属性
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);//设置标题栏左侧有个返回键按钮
            }
        }

        if (Build.VERSION.SDK_INT >= 21){
            mAppBar.setElevation(10.6f);//想api为21以下的版本兼容
        }
    }

    public boolean canBack(){
        return false;
    }

    public boolean isWebView(){
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isWebView()){
            getMenuInflater().inflate(R.menu.toolbar_menu, menu);
            return true;
        }else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
