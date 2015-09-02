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
public class SecretaryFragment extends Fragment{

    private final String TAG = getClass().getSimpleName();
    private View rootView;
    private ImageView mImgSecretary;
    private DisplayImageOptions displayImageOptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_secretary, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayImageOptions = MainActivity.instance.displayImageOptions;
        mImgSecretary = (ImageView) rootView.findViewById(R.id.fragment_secretary_img);
        ImageLoader.getInstance().displayImage("http://pica.nipic.com/2008-05-22/2008522164145981_2.jpg", mImgSecretary);
    }
}
