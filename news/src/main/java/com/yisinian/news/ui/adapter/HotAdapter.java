package com.yisinian.news.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yisinian.news.R;
import com.yisinian.news.api.ApiConstants;
import com.yisinian.news.bean.Daily;
import com.yisinian.news.common.Constants;
import com.yisinian.news.ui.activity.WebActivity;
import com.yisinian.news.utils.NewsLog;

import java.util.ArrayList;

/**
 * Created by deng on 2015/9/6.
 */
public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ItemViewHolder>{

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
            NewsLog.i(TAG,"---> " + mList.size());
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
    public void onBindViewHolder(ItemViewHolder holder, int position) {
       final Daily daily = mList.get(position);
        holder.mTv.setText(daily.title);
//        ImageLoader.getInstance().displayImage(daily.images.get(0), holder.mImg);

    }

    @Override
    //用来创建一个视图
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.hot_recycler_item, parent,false);
        return new ItemViewHolder(view);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mTv;
        public ImageView mImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.item);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
            mImg = (ImageView) itemView.findViewById(R.id.item_img);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//点击新闻条目进入新闻详情WebActivity
                    Daily news = mList.get(getLayoutPosition());
                    String newsUrl = ApiConstants.getDailyNewsContentUrl(news.id);
                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra(Constants.CONTENT_TTTLE, news.title);
                    intent.putExtra(Constants.CONTENT_URL, newsUrl);
                    v.getContext().startActivity(intent);
                    NewsLog.e(TAG,"---> v.getContent().startActivity(inent)");
                }
            });
        }
    }

}
