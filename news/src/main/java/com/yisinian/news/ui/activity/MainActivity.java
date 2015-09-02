package com.yisinian.news.ui.activity;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yisinian.news.R;
import com.yisinian.news.ui.fragment.HotFragment;
import com.yisinian.news.ui.fragment.SchoolFragment;
import com.yisinian.news.ui.fragment.SecretaryFragment;
import com.yisinian.news.utils.DensityUtils;
import com.yisinian.news.utils.DisplayOptions;
import com.yisinian.news.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    private final String TAG = getClass().getSimpleName();
    public static MainActivity instance;
    private Toolbar mToolbar;
    private RadioGroup mRadioGroup;
    private RadioButton mHotRbtn, mSchoolRbtn, mSecretaryRbtn;
    private ViewPager mViewPager;
    public DisplayImageOptions displayImageOptions;
    private ImageView mImg;
    private FragmentPagerAdapter fpAdapter;
    private List<Fragment> mFragments;
    private TextView mTab;
    private float mTabWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        displayImageOptions = DisplayOptions.getlistoptions();
        initView();
        initToolbar();
        initFragment();
        initViewPager();
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mRadioGroup = (RadioGroup) findViewById(R.id.main_choice_rg);
        mHotRbtn = (RadioButton) findViewById(R.id.main_hot_rb);
        mSchoolRbtn = (RadioButton) findViewById(R.id.main_school_rb);
        mSecretaryRbtn = (RadioButton) findViewById(R.id.main_secrety_rb);
        mTab = (TextView) findViewById(R.id.main_tab);
        mViewPager = (ViewPager) findViewById(R.id.main_content_vp);
        mHotRbtn.setOnClickListener(this);
        mSchoolRbtn.setOnClickListener(this);
        mSecretaryRbtn.setOnClickListener(this);
        setSelect(0);
    }

    private void initToolbar(){
        mToolbar.setTitle("忆斯年日报");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.activity_main_color_text_click));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.user_avatar);

        //点击用户个人信息
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void initFragment(){

        mFragments = new ArrayList<Fragment>();
        Fragment hot = new HotFragment();
        Fragment school = new SchoolFragment();
        Fragment secretary = new SecretaryFragment();
        mFragments.add(hot);
        mFragments.add(school);
        mFragments.add(secretary);

    }

    private void initViewPager(){

        fpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 :mFragments.size();
            }
        };

        mViewPager.setAdapter(fpAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法会一直得到调用
//                positionOffsetPixels表示偏移的像素位置
//                setPagerScrolled(position, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                int index = mViewPager.getCurrentItem();
                setTab(index);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setPagerScrolled(int position, int positionOffsetPixels) {
        float temp = 0;
        float temp2 = 0;
        if (positionOffsetPixels != 0 && position == 0) {
            mTab.setTranslationX((float) positionOffsetPixels / 3);
            temp = mTab.getX();
        } else if (positionOffsetPixels != 0 && position == 1) {
            mTab.setTranslationX((float) positionOffsetPixels / 3 + temp);
            temp2 = mTab.getX();
        } else if (positionOffsetPixels != 0 && position == 2){
            mTab.setTranslationX((float) positionOffsetPixels / 3 + temp2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTabLine();
    }

    private void setTab(int i){

        setReset();

        switch (i){
            case 0:
                mHotRbtn.setTextColor(getResources().getColor(R.color.activity_main_color_text_click));
                mTab.setBackgroundResource(R.color.activity_main_color_text_click);
                startAnimation(mTab, 0);
                break;
            case 1:
                mSchoolRbtn.setTextColor(getResources().getColor(R.color.activity_main_color_text_click));
                mTab.setBackgroundResource(R.color.activity_main_color_text_click);
                startAnimation(mTab, 1);
                break;
            case 2:
                mSecretaryRbtn.setTextColor(getResources().getColor(R.color.activity_main_color_text_click));
                mTab.setBackgroundResource(R.color.activity_main_color_text_click);
                startAnimation(mTab, 2);
                break;
        }

    }

    //set radiogroup line
    private void setTabLine(){
        int screenWidth = DensityUtils.getDisplayWidth(MainActivity.this);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(screenWidth/3, 10);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(layoutParams);
        mTab.setLayoutParams(layoutParams1);
        mTabWidth = screenWidth /3;
    }

    private void startAnimation(View view, int offset){
        ValueAnimator animator = ObjectAnimator.ofFloat(view,"translationX", view.getX(), mTabWidth * offset);
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.main_hot_rb:
                setSelect(0);
                break;

            case R.id.main_school_rb:
                setSelect(1);
                break;

            case R.id.main_secrety_rb:
                setSelect(2);
                break;
        }
    }

    private void setSelect(int i){
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    private void setReset(){
        mHotRbtn.setTextColor(getResources().getColor(R.color.activity_main_color_text_normal));
        mSchoolRbtn.setTextColor(getResources().getColor(R.color.activity_main_color_text_normal));
        mSecretaryRbtn.setTextColor(getResources().getColor(R.color.activity_main_color_text_normal));
    }

}
