package com.yisinian.news.views;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by deng on 2015/9/9.
 */
public class ScrollWebView extends WebView{

    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null){
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback(){
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback){
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public static interface OnScrollChangedCallback{

        //dx和dy分别是滚动的时候x和y方向上的偏移量
        public void onScroll(int dx, int dy);
    }
}
