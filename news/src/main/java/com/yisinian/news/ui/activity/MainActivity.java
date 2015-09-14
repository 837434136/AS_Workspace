package com.yisinian.news.ui.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.OauthHelper;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.yisinian.news.NewsApplications;
import com.yisinian.news.R;
import com.yisinian.news.common.Constants;
import com.yisinian.news.ui.adapter.FragmentAdapter;
import com.yisinian.news.ui.fragment.HotFragment;
import com.yisinian.news.ui.fragment.SchoolFragment;
import com.yisinian.news.ui.fragment.SecretaryFragment;
import com.yisinian.news.utils.CommonUtils;
import com.yisinian.news.utils.DensityUtils;
import com.yisinian.news.utils.DisplayOptions;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.UMENGSHARE);
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
    private SharedPreferences sharedPreferences;

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
        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(MainActivity.this,appID,appSecret);
        wxHandler.addToSocialSDK();
// 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(MainActivity.this,appID,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCES, MODE_PRIVATE);
        // 添加QQ平台
        UMQQSsoHandler qqHandler = new UMQQSsoHandler(MainActivity.this,
                "100424468", "c7394704798a158208a74ab60104f0ba");
        qqHandler.addToSocialSDK();
        // 添加QQ空间平台
        QZoneSsoHandler qzoneHandler = new QZoneSsoHandler(MainActivity.this,
                "100424468", "c7394704798a158208a74ab60104f0ba");
        qzoneHandler.addToSocialSDK();
        mController.setShareContent("BOoM boOM bOoM~~");
//        SinaShareContent sinaShareContent = new SinaShareContent();
//        sinaShareContent.setShareContent("BOoM boOM bOoM~~");
//        mController.setShareMedia(sinaShareContent);
        mController.setShareMedia(new UMImage(MainActivity.this,
                "http://img4.duitang.com/uploads/item/201306/16/20130616224058_HXRJx.thumb.600_0.jpeg"));
        mController.getConfig().setPlatforms(SHARE_MEDIA.QQ, SHARE_MEDIA.SINA,SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE);

//                        mController.setShareMedia(new UMImage(MainActivity.this, BitmapFactory.decodeFile(
//                                "/mnt/sdcard/icon.png")));
//                         设置分享音乐
//                        UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
//                        uMusic.setAuthor("GuGu");
//                        uMusic.setTitle("天籁之音");
//                         设置音乐缩略图
//                        uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
//                        mController.setShareMedia(uMusic);
//
//                         设置分享视频
//                        UMVideo umVideo = new UMVideo(
//                                  "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
//                         设置视频缩略图
//                        umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
//                        umVideo.setTitle("友盟社会化分享!");
//                        mController.setShareMedia(umVideo);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setMinimumHeight(200);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_content_vp);
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.main_toolbar_title);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.activity_main_color_text_click));
        setSupportActionBar(mToolbar);
        //点击用户个人信息
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareIntent(SettingActivity.class);
                finish();
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

    private void setIcon() {
        NewsLog.e("yue_chou", "inSetIcon");
        NewsLog.e("yue_chou", sharedPreferences.getString(Constants.SETTING_STATUS,Constants.VISITOR));
        String status = sharedPreferences.getString(Constants.SETTING_STATUS, Constants.VISITOR);
        if (!status.equals(Constants.VISITOR)) {
            NewsLog.e("yue_chou","no visitor");
            NewsLog.e("yue_chou", getSharedPreferences(Constants.SHAREDPREFERENCES, MODE_PRIVATE)
                    .getString(Constants.IMAGE_URL, ""));

            ImageLoader.getInstance().loadImage(getSharedPreferences(Constants.SHAREDPREFERENCES, MODE_PRIVATE)
                    .getString(Constants.IMAGE_URL, ""), DisplayOptions.getlistoptions(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    Bitmap bm = Bitmap.createScaledBitmap(bitmap, DensityUtils.dip2px(MainActivity.this,
                                    CommonUtils.getToolbarHeight(MainActivity.this)),
                            DensityUtils.dip2px(MainActivity.this, CommonUtils.getToolbarHeight(MainActivity.this)), false);
                    bm.isRecycled();
                    mToolbar.setNavigationIcon(new BitmapDrawable(bm));
            }

                @Override
                public void onLoadingCancelled(String s, View view) {

            }
        });

    }else {
        NewsLog.e("yue_chou", "user_avatar");
        NewsLog.e("yue_chou", getSharedPreferences(Constants.SHAREDPREFERENCES, MODE_PRIVATE)
                    .getString(Constants.IMAGE_URL, ""));
            //初始化
            mToolbar.setNavigationIcon(R.mipmap.user_avatar);
        }
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

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * 单点登录(Single sign on)→SSO
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }


    }

    private void fastShare(SHARE_MEDIA share_media) {
        if (OauthHelper.isAuthenticated(MainActivity.this, share_media)){
            mController.directShare(MainActivity.this, share_media,
                    new SocializeListeners.SnsPostListener() {
                        @Override
                        public void onStart() {
                            ToastUtils.showShort("开始分享...");
                        }

                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                            if (i == StatusCode.ST_CODE_SUCCESSED) {
                                ToastUtils.showShort("分享成功!");
                            } else {
                                ToastUtils.showShort("分享失败!");
                            }
                        }
                    });
        }else {
            ToastUtils.showShort("请先登录.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setIcon();
    }
}

/*
备用
mController.postShare(MainActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.SnsPostListener() {
@Override
public void onStart() {

        }

@Override
public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
        if (i == 200) {
        Toast.makeText(MainActivity.this, "分享成功", Toast.LENGTH_SHORT)
        .show();
        } else {
        Toast.makeText(MainActivity.this,
        "分享失败 : error code : " + i, Toast.LENGTH_SHORT)
        .show();
        }
        }
        });*/
