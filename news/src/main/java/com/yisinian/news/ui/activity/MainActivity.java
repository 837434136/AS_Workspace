package com.yisinian.news.ui.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yisinian.news.R;
import com.yisinian.news.utils.DisplayOptions;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.utils.ToastUtils;


public class MainActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private TextView mTv;
    private DisplayImageOptions displayImageOptions = DisplayOptions.getlistoptions();
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(displayImageOptions).build();
        ImageLoader.getInstance().init(configuration);
        imageView = (ImageView) findViewById(R.id.image11);
        ImageLoader.getInstance().displayImage("http://pica.nipic.com/2007-11-09/2007119124413448_2.jpg", imageView);
        mTv = (TextView)findViewById(R.id.tv);
        mTv.setText("啊猫是大傻逼.....");

        mTv.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
        ToastUtils.showShort("MianActivity oncreate()");
        NewsLog.e(TAG, "oncreate()");
    }

}
