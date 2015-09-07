package com.yisinian.news.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yisinian.news.R;
import com.yisinian.news.common.Constants;
import com.yisinian.news.utils.DisplayOptions;

/**
 * Created by Administrator on 2015/9/7.
 */
public class LoginSettingAdapter extends BaseAdapter {
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    Context mContext;
    LayoutInflater inflater;
    final int TYPE_1 = 1;//头像行
    final int TYPE_5 = 5;//检查更新行
    final int TYPE_6 = 6;//退出登陆行

    public LoginSettingAdapter(Context context) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        sharedpreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES,Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;
        switch (position) {
            case 0:
                result = TYPE_1;
                break;
            case 1:
                result = TYPE_5;
                break;
            case 2:
                result = TYPE_6;
                break;
        }
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder1 holder1 = null;
        viewHolder2 holder2 = null;
        int type = getItemViewType(position);

        //若无convertView,需要new出各个控件
        if (convertView == null) {

            switch (type) {
                case TYPE_1:
                    convertView = inflater.inflate(R.layout.setting_item1, parent, false);
                    holder1 = new viewHolder1();
                    holder1.ivIcon = (ImageView) convertView.findViewById(R.id.icon_iv);
                    holder1.tvIcon = (TextView) convertView.findViewById(R.id.icon_tv);
                    convertView.setTag(holder1);
                    break;
                case TYPE_5:
                case TYPE_6:
                    convertView = inflater.inflate(R.layout.setting_item2, parent, false);
                    holder2 = new viewHolder2();
                    holder2.tvTitle = (TextView) convertView.findViewById(R.id.title_tv);
                    holder2.ivDetails = (ImageView) convertView.findViewById(R.id.detail_iv);
                    convertView.setTag(holder2);
                    break;
                default:
                    break;
            }

        } else {
            switch (type) {
                case TYPE_1:
                    holder1 = (viewHolder1) convertView.getTag();
                    break;
                case TYPE_5:
                case TYPE_6:
                    holder2 = (viewHolder2) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case TYPE_1:
                String imageUrl = sharedpreferences.getString("image_url", "http://i1.hdslb.com/user/22584/2258490/myface.jpg");
                String userName = sharedpreferences.getString("user_name", "智障");
                ImageLoader.getInstance().displayImage(imageUrl, holder1.ivIcon, DisplayOptions.getlistoptions());
                holder1.tvIcon.setText(Html.fromHtml("<u>" + "userName" + "</u>"));
                break;
            case TYPE_5:
                holder2.tvTitle.setText("版本更新");
                holder2.tvTitle.setTextColor(Color.GRAY);
                holder2.ivDetails.setVisibility(View.GONE);
                break;
            case TYPE_6:
                holder2.tvTitle.setText("退出登录");
                holder2.tvTitle.setTextColor(Color.RED);
                holder2.ivDetails.setVisibility(View.GONE);
                break;
        }

        return convertView;

    }


    class viewHolder1 {
        TextView tvIcon;
        TextView tvLine;
        ImageView ivIcon;
    }

    class viewHolder2 {
        TextView tvTitle;
        ImageView ivDetails;
    }
}
