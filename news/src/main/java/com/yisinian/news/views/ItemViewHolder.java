package com.yisinian.news.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yisinian.news.R;

/**
 * Created by deng on 2015/9/6.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder{

    public static TextView mTv;
    public static ImageView mImg;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mTv = (TextView) itemView.findViewById(R.id.item_tv);
        mImg = (ImageView) itemView.findViewById(R.id.item_img);
    }
}
