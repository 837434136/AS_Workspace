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
import com.yisinian.news.R;
import com.yisinian.news.common.Constants;
import com.yisinian.news.ui.adapter.LoginSettingAdapter;
import com.yisinian.news.ui.adapter.VisitorSettingAdapter;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.utils.ToastUtils;

import java.util.Map;


/**
 * Created by HandsomeBoy on 2015/9/1.
 */
public class SettingActivity extends BaseActivity {

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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        NewsLog.e(TAG, status);
        if (status.equals(Constants.VISITOR)) {
            myAdapter = new VisitorSettingAdapter(getApplicationContext());
            listView.setAdapter(myAdapter);
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
                            ToastUtils.showShort("click 微信登陆...");
                            break;
                        case 3:
                            ToastUtils.showShort("click 版本更新...");
                            break;
                    }
                }
            });
        }else {
            switch (status) {
                case Constants.QQ:
                    getUserInfo(SHARE_MEDIA.QQ);
                    break;
                case Constants.WEIXIN:
                    getUserInfo(SHARE_MEDIA.WEIXIN);
                    break;
                case Constants.SINA:
                    getUserInfo(SHARE_MEDIA.SINA);
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
//        UMWXHandler wxHandler = new UMWXHandler(SettingActivity.this,appId,appSecret);
//        wxHandler.addToSocialSDK();
        initToolbar();
        sharedpreferences = SettingActivity.this.getSharedPreferences(Constants.SHAREDPREFERENCES, MODE_PRIVATE);
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
                    getUserInfo(platform);
                    SettingActivity.this.finish();
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
    private void getUserInfo(final SHARE_MEDIA platform) {
        mController.getPlatformInfo(SettingActivity.this, platform, new SocializeListeners.UMDataListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                String showText = "用户名：" + info.get("screen_name").toString();
                if (info != null) {
                    switch (platform.toString()){
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
                    editor.putString("user_name", info.get("screen_name").toString());
                    editor.putString("image_url", info.get("profile_image_url").toString());
                    editor.commit();
                    NewsLog.e(TAG, info.toString());
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
                }else {
                    editor.putString(Constants.SETTING_STATUS, Constants.VISITOR);
                    editor.commit();
                    ImageLoader.getInstance().clearDiskCache();
                    refresh();
                }
                ToastUtils.showShort(showText);
            }
        });
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