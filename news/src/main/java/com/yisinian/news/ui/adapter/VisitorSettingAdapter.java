package com.yisinian.news.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yisinian.news.R;

/**
 * Created by HandsomeBoy on 2015/9/7.
 */
public class VisitorSettingAdapter extends BaseAdapter{
        Context mContext;
        LayoutInflater inflater;
        //        final int TYPE_1 = 1;//头像行
        final int TYPE_2 = 2;//微博登录
        final int TYPE_3 = 3;//QQ行
        final int TYPE_4 = 4;//微信行
        final int TYPE_5 = 5;//检查更新行
//        final int TYPE_6 = 6;//退出登陆行

        public VisitorSettingAdapter(Context context) {
            this.mContext = context;
            this.inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public int getItemViewType(int position) {
            int result = 0;
            switch (position) {
                case 0:
                    result = TYPE_2;
                    break;
                case 1:
                    result = TYPE_3;
                    break;
                case 2:
                    result = TYPE_4;
                    break;
                case 3:
                    result = TYPE_5;
                    break;
            }
            return result;
        }

        @Override
        public int getViewTypeCount() {
            return 5;
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
                    case TYPE_2:
                    case TYPE_3:
                    case TYPE_4:
                    case TYPE_5:
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
                    case TYPE_2:
                    case TYPE_3:
                    case TYPE_4:
                    case TYPE_5:
                        holder2 = (viewHolder2) convertView.getTag();
                        break;
                }
            }

            switch (type) {
                case TYPE_2:
                    holder2.tvTitle.setText("微博登录");
                    holder2.ivDetails.setImageResource(R.mipmap.sina);
                    break;
                case TYPE_3:
                    holder2.tvTitle.setText("QQ登录");
                    holder2.ivDetails.setImageResource(R.mipmap.tencent);
                    break;
                case TYPE_4:
                    holder2.tvTitle.setText("微信登录");
                    holder2.ivDetails.setImageResource(R.mipmap.wechat);
                    break;
                case TYPE_5:
                    holder2.tvTitle.setText("版本更新");
                    holder2.tvTitle.setTextColor(Color.GRAY);
                    holder2.ivDetails.setVisibility(View.GONE);
                    break;
            }

            return convertView;

        }


        class viewHolder1 {
            TextView tvIcon;
            ImageView ivIcon;
        }

        class viewHolder2 {
            TextView tvTitle;
            ImageView ivDetails;
        }
    }
