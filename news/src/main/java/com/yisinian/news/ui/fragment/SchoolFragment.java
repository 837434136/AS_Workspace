package com.yisinian.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yisinian.news.R;
import com.yisinian.news.ui.activity.MainActivity;

/**
 * Created by deng on 2015/9/1.
 */
public class SchoolFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private View rootView;
    private ImageView mImgSchool;
    private DisplayImageOptions displayImageOptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_school, container,false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayImageOptions = MainActivity.instance.displayImageOptions;
        mImgSchool = (ImageView) rootView.findViewById(R.id.fragment_school_img);
        ImageLoader.getInstance().displayImage("http://b.hiphotos.baidu.com/image/pic/item/35a85edf8db1cb13f00ced1adf54564e92584b56.jpg", mImgSchool);
    }
}
