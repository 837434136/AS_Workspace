package com.yisinian.deng.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deng on 2015/8/26.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int HEADER = 0;//第一个空View
    private static final int ITEM = 1;//正式的item
    private List<String> list;

    public RecycleAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEADER;
        }
        return  ITEM;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size()+1;
    }

    @Override
    //用来把数据绑定到视图上
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0){//正常的item
            String item = list.get(position - 1);//header
            ItemViewHolder.mTv.setText(item);
        }
    }

    @Override
    //用来创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = new View(parent.getContext());
        Context context = parent.getContext();
        if (viewType == HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
            return new ItemViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");

    }
}
