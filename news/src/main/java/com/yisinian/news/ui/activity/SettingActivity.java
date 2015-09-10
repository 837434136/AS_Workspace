package com.yisinian.news.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.OauthHelper;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.yisinian.news.R;
import com.yisinian.news.common.Constants;
import com.yisinian.news.ui.adapter.LoginSettingAdapter;
import com.yisinian.news.ui.adapter.VisitorSettingAdapter;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.utils.ToastUtils;

import java.util.Map;


/**
 * 新浪微博、腾讯微博、豆瓣、人人网、QQ在执行分享前需要先进行授权操作， 其他平台可以直接调用分享API接口。
 * QQ分享时不支持纯图片分享
 * Created by HandsomeBoy on 2015/9/1.
 */
public class SettingActivity extends BaseActivity {

    private static final int JUMP = 28;
    private final String TAG = getClass().getSimpleName();
    private UMSocialService mController = UMServiceFactory
            .getUMSocialService(Constants.UMENGLOGIN);
    private VisitorSettingAdapter myAdapter;
    private LoginSettingAdapter myAdapter2;
    private ListView listView;
    private Toolbar mToolbar;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private String status;
    private Boolean ISLOGIN = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        NewsLog.e(TAG, status);
        if (status.equals(Constants.VISITOR)) {
            myAdapter = new VisitorSettingAdapter(getApplicationContext());
            listView.setAdapter(myAdapter);
//            listView.setDividerHeight(0);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            login(SHARE_MEDIA.SINA);
                            break;
                        case 1:
                            login(SHARE_MEDIA.QQ);
                            break;
                        case 2:
                            login(SHARE_MEDIA.WEIXIN);
                            break;
                        case 3:
                            ToastUtils.showShort("click 版本更新...");
                            break;
                    }
                }
            });
        }else if (OauthHelper.isAuthenticated(SettingActivity.this, SHARE_MEDIA.SINA)
                || OauthHelper.isAuthenticated(SettingActivity.this, SHARE_MEDIA.QQ)
                || OauthHelper.isAuthenticated(SettingActivity.this, SHARE_MEDIA.WEIXIN) ){
            switch (status) {
                case Constants.QQ:
                    getUserInfo(SHARE_MEDIA.QQ, false);
                    break;
                case Constants.WEIXIN:
                    getUserInfo(SHARE_MEDIA.WEIXIN, false);
                    break;
                case Constants.SINA:
                    getUserInfo(SHARE_MEDIA.SINA, false);
                    break;
            }
            myAdapter2 = new LoginSettingAdapter(getApplicationContext());
            listView.setAdapter(myAdapter2);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            view.findViewById(R.id.icon_iv).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtils.showShort("click icon...");
                                }
                            });
                            break;
                        case 1:
                            ToastUtils.showShort("click 版本更新...");
                            break;
                        case 2://退出登陆
                            switch (status) {
                                case Constants.QQ:
                                    logout(SHARE_MEDIA.QQ);
                                    break;
                                case Constants.WEIXIN:
                                    logout(SHARE_MEDIA.WEIXIN);
                                    break;
                                case Constants.SINA:
                                    logout(SHARE_MEDIA.SINA);
                                    break;
                            }
                    }
                }
            });
        }

    }

    private void refresh() {
        startActivity(new Intent(SettingActivity.this, SettingActivity.class));
        finish();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list_setting);
        mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(SettingActivity.this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();

        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(SettingActivity.this,appID,appSecret);
        wxHandler.addToSocialSDK();
// 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(SettingActivity.this,appID,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        initToolbar();
        sharedpreferences = getSharedPreferences(Constants.SHAREDPREFERENCES, MODE_PRIVATE);
        editor = sharedpreferences.edit();
        status = sharedpreferences.getString(Constants.SETTING_STATUS,Constants.VISITOR);
    }


    private void initToolbar() {

        mToolbar.setTitle("设置");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.activity_main_color_text_click));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.return_normal);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareIntent(MainActivity.class);
                finish();
            }
        });

    }

    private void login(final SHARE_MEDIA platform) {

        mController.doOauthVerify(SettingActivity.this, platform, new SocializeListeners.UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {
                ToastUtils.showShort("授权开始");
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                ToastUtils.showShort("授权失败");
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                String uid = value.getString("uid");
                String openid = value.getString("openid");
                if (!TextUtils.isEmpty(uid)) {
                    getUserInfo(platform, true);

                } else {
                    ToastUtils.showShort("授权失败");
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                ToastUtils.showShort("授权取消");
            }
        });
    }


    /**
     * 获取授权平台的用户信息</br>
     */
    private void getUserInfo(final SHARE_MEDIA platform, final Boolean isLogin) {
        mController.getPlatformInfo(SettingActivity.this, platform, new SocializeListeners.UMDataListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                if (info != null) {
                    switch (platform.toString()) {
                        case Constants.SINA:
                            editor.putString(Constants.SETTING_STATUS, Constants.SINA);
                            break;
                        case Constants.QQ:
                            editor.putString(Constants.SETTING_STATUS, Constants.QQ);
                            break;
                        case Constants.WEIXIN:
                            editor.putString(Constants.SETTING_STATUS, Constants.WEIXIN);
                            break;
                    }
                    editor.putString(Constants.USERNAME, info.get("screen_name").toString());
                    editor.putString(Constants.IMAGE_URL, info.get("profile_image_url").toString());
                    editor.commit();
                    if (isLogin) {
                        prepareIntent(MainActivity.class);
                        finish();
                    }
                    NewsLog.e(TAG, info.get("profile_image_url").toString());

                }
            }
        });
    }

    /**
     * 注销本次登录</br>
     */
    private void logout(final SHARE_MEDIA platform) {
        mController.deleteOauth(SettingActivity.this, platform, new SocializeListeners.SocializeClientListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                String showText = "解除" + platform.toString() + "平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
                } else {
                    editor.putString(Constants.SETTING_STATUS, Constants.VISITOR);
                    editor.commit();
                    ImageLoader.getInstance().clearDiskCache();
                    refresh();
                }
                ToastUtils.showShort(showText);
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
        startActivity(new Intent(SettingActivity.this, clz));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        prepareIntent(MainActivity.class);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}