package com.yisinian.news.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yisinian.news.NewsApplications;
import com.yisinian.news.R;
import com.yisinian.news.ui.adapter.FragmentAdapter;
import com.yisinian.news.ui.fragment.HotFragment;
import com.yisinian.news.ui.fragment.SchoolFragment;
import com.yisinian.news.ui.fragment.SecretaryFragment;
import com.yisinian.news.utils.DisplayOptions;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    public static MainActivity instance;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private AppBarLayout mAppBarLayout;
    private ViewPager mViewPager;
    private CoordinatorLayout coordinatorLayout;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    public DisplayImageOptions displayImageOptions;
    private FragmentAdapter fpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResID = R.layout.activity_main;
        super.onCreate(savedInstanceState);
        NewsLog.i(TAG, "--->onCreate()");
        instance = this;
        displayImageOptions = DisplayOptions.getlistoptions();
        initView();
        initToolbar();
        initFragment();
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_content_vp);
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.main_toolbar_title);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.activity_main_color_text_click));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.user_avatar);

        //点击用户个人信息
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareIntent(SettingActivity.class);
            }
        });

        //点击查找或者添加
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
                    case R.id.action_search:
                        msg += "Click search";
                        break;
                    case R.id.action_add:
                        msg += "Click add";
                        break;
                }

                if (!TextUtils.isEmpty(msg)) {
                    ToastUtils.showShort(msg);
                }
                return true;
            }
        });
    }

    /**
     * 该方法用作跳转是调用
     *
     * @param clz
     * @return
     */
    private boolean prepareIntent(Class clz) {
        startActivity(new Intent(MainActivity.this, clz));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    private void initFragment() {

        //初始化TabLayout的title的数据集
        mTitles = new ArrayList<String>();
        mTitles.add(getResources().getString(R.string.main_hot));
        mTitles.add(getResources().getString(R.string.main_school));
        mTitles.add(getResources().getString(R.string.main_secretary));

        //初始化TabLayout的title
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(2)));

        //初始化ViewPager的数据集
        mFragments = new ArrayList<Fragment>();
        Fragment hot = HotFragment.getInstance();
        Fragment school = new SchoolFragment();
        Fragment secretary = new SecretaryFragment();
        mFragments.add(hot);
        mFragments.add(school);
        mFragments.add(secretary);

        //创建ViewPager的adapter
        fpAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(fpAdapter);
        //关联TabLayout与ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(fpAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * 实现再按一次退出应用
     */
    private long exitTime = 0;
    private long INVERTAL_TIME = 2000;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return exitApp(keyCode, event);
    }

    private boolean exitApp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > INVERTAL_TIME) {
                ToastUtils.showShort("再按一次退出登录");
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
                NewsApplications.getInstance().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
