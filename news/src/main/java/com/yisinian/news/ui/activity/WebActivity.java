package com.yisinian.news.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.bean.LIKESTATUS;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.yisinian.news.R;
import com.yisinian.news.common.Constants;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.views.ScrollWebView;

/**
 * Created by deng on 2015/9/8.
 */
public class WebActivity extends ToolbarActivity implements View.OnClickListener{

    private final String TAG = getClass().getSimpleName();
    private Context context;
    private AppBarLayout mAppBar;
    private ProgressBar mProgressBar;
    private String  mTitle, mUrl;
    private ScrollWebView mWebView;
    private RelativeLayout mRlBottom;
    private LinearLayout mLlShare, mLlThumb, mLlFont;
    private ImageView mImgThumb;
    private TextView mTvThumb;
    private LIKESTATUS admitStatus;

    //滑动距离大于20才产生动画效果，不然体验不好
    private static final int HIDE_THRESHOLD = 5;
    private boolean isShow = true;//是否可见
    private int disy = 0;//监听滑动的距离

    //umeng thumb conut
    private UMSocialService mController;

    @Override
    protected int getLayoutResource() {//填充一个布局文件
        return R.layout.activity_webview;
    }

    @Override
    public boolean canBack() {//设置该界面可以返回上一级界面
        return true;
    }

    @Override
    public boolean isWebView() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mTitle = getIntent().getStringExtra(Constants.CONTENT_TTTLE);
        mUrl = getIntent().getStringExtra(Constants.CONTENT_URL);
        mController = UMServiceFactory.getUMSocialService(mUrl);

        initView();
        setWebView();
        addListener();

        mWebView.setOnScrollChangedCallback(new ScrollWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                //to do what you want
                showAndHideBottom(dy);
            }
        });

    }

    private void addListener() {
        mLlThumb.setOnClickListener(this);
    }

    private void initView() {
        mAppBar = (AppBarLayout) findViewById(R.id.base_app_bar_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.web_progressbar);
        mWebView = (ScrollWebView) findViewById(R.id.web_view);
        mRlBottom = (RelativeLayout) findViewById(R.id.web_Rl_bottom);
        mLlThumb = (LinearLayout) findViewById(R.id.web_ll_thumb);
        mImgThumb = (ImageView) findViewById(R.id.web_img_thumb);
        mTvThumb = (TextView) findViewById(R.id.web_tv_thumb);


        admitStatus = mController.getEntity().getLikeStatus();
        if (admitStatus ==  LIKESTATUS.LIKE){
            mImgThumb.setBackgroundResource(R.mipmap.icon_thumbed);
            mLlThumb.setEnabled(false);
        }
        int likeCount = mController.getEntity().getLikeCount();
        mTvThumb.setText(likeCount + "");
    }

    private void setWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new ViewClient());
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.loadUrl(mUrl);
        setTitle(mTitle);
    }

    private void showAndHideBottom(int dy) {
        if (dy > HIDE_THRESHOLD && isShow){//手指向上滑动，dy是正的
            //隐藏底部
            onHide();
            isShow = false;
            dy = 0;//归零
        }

        if (dy < -HIDE_THRESHOLD && !isShow){//手指向下滑动， dy是负的
            onShow();
            isShow = true;
            dy = 0;
        }

        //计算滚动的总距离，叠加（dy > 0 是下滚，dy < 0 上滚）
        if ((isShow && dy > 0) || (!isShow && dy < 0)){//可见&&下滚   不可见&&上滚
            dy += dy;
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.web_ll_thumb:
                mController.likeChange(WebActivity.this, new SocializeListeners.SocializeClientListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(int i, SocializeEntity socializeEntity) {
                        mImgThumb.setBackgroundResource(R.mipmap.icon_thumbed);
                        int likeCount = socializeEntity.getLikeCount();
                        mTvThumb.setText((likeCount + 1) + "");
                        NewsLog.e(TAG, "--->mController onComplete");
                    }
                });
                break;
        }
    }

    /**
     * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等比如
     *onCloseWindow(关闭WebView)
     *onCreateWindow()
     *onJsAlert (WebView上alert无效，需要定制WebChromeClient处理弹出)
     *onJsPrompt
     *onJsConfirm
     *onProgressChanged
     *onReceivedIcon
     *onReceivedTitle
     */
    private class ChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100){
                mProgressBar.setVisibility(View.GONE);
            }else if(newProgress != 100){
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);//设置新闻标题
        }
    }

    /**
     * 主要帮助WebView处理各种通知、请求时间的。比如：onLoadResource， onPageStart，onPageFinish，onReceiverError
     * onReceiverdHttpAuthRequest
     */
    private class ViewClient extends WebViewClient{
        public boolean isOverrideUrlLoading(WebView v, String url){
            if (!TextUtils.isEmpty(url)){
                v.loadUrl(url);
            }
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()){
                        mWebView.goBack();
                    }else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onHide(){
        mRlBottom.animate().translationY(mRlBottom.getHeight()).setDuration(1000).start();
    }

    private void onShow(){
        mRlBottom.animate().translationY(0).setDuration(1000).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null){
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null){
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }
}
