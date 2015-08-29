package com.yisinian.news.ui.activity;


import android.os.Bundle;
import android.widget.TextView;

import com.yisinian.news.R;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.utils.ToastUtils;


public class MainActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView)findViewById(R.id.tv);
        mTv.setText("啊猫是大傻逼.....");
//        mTv.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
        ToastUtils.showShort("NewsApplication oncreate()");
        NewsLog.i(TAG, " oncreate()");
    }

}
