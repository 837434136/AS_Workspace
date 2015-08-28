package com.yisinian.deng.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by java on 2015/8/27.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder{

    public static TextView mTv;
    public ItemViewHolder(View itemView) {
        super(itemView);
        mTv = (TextView) itemView.findViewById(R.id.itemTv);
    }
}