package com.yisinian.news.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yisinian.news.R;
import com.yisinian.news.bean.Daily;
import com.yisinian.news.utils.NewsLog;
import com.yisinian.news.views.ItemViewHolder;

import java.util.ArrayList;

/**
 * Created by deng on 2015/9/6.
 */
public class HotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final String TAG = getClass().getSimpleName();
    private Context context;
    private ArrayList<Daily> mList;

    public HotAdapter(Context context, ArrayList<Daily> lists) {
        this.context = context;
        setData(lists);
    }

    private void setData(ArrayList<Daily> lists) {
        if (lists == null){
            this.mList = new ArrayList<Daily>();
        }else{
            this.mList = lists;
        }
    }

    public void updateData(ArrayList<Daily> lists){
        setData(lists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 :mList.size();
    }

    @Override
    //用来把数据绑定到视图上
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Daily daily = mList.get(position);
        NewsLog.i(TAG, "---> positon :" + position);
        ItemViewHolder.mTv.setText(daily.title);
        ImageLoader.getInstance().displayImage(daily.images.get(0), ItemViewHolder.mImg);

    }

    @Override
    //用来创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.hot_recycler_item, parent,false);
        return new ItemViewHolder(view);
    }

}
