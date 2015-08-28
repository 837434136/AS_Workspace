package com.yisinian.deng.myapplication;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by deng on 2015/8/26.
 */
public abstract class RecyclerScrollListener extends RecyclerView.OnScrollListener {

    //滑动距离大于20才产生动画效果，不然体验不好
    private static final int HIDE_THRESHOLD = 20;
    private boolean isShow = true;//是否可见
    private int firstVisibleItem;//第一个可见Item下标
    private int disy = 0;//监听滑动的距离

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        super.onScrolled(recyclerView, dx, dy);
        firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItem == 0){//如果是第一条空view，则不管上滑或者下滑都显示出来
            if (!isShow){//如果此时不在显示中 得显示
                onShow();
                isShow = true;
            }
        }else {//不是第一条空view

            if (disy > HIDE_THRESHOLD && isShow){//手指向上滑动，dy是正的，距离大于20且显示状态才产生动画效果隐藏
                onHide();
                isShow = false;
                disy = 0;//归零
            }

            if (disy < -HIDE_THRESHOLD && !isShow){//手指向下滑动， dy是负的，距离大于20且隐藏状态才产生动画效果显示
                onShow();
                isShow = true;
                disy = 0;
            }
        }

        //计算滚动的总距离，叠加（dy > 0 是下滚，dy < 0 上滚）
        if ((isShow && dy > 0) || (!isShow && dy < 0)){//可见&&下滚   不可见&&上滚
            disy += dy;
        }
    }

    //这个是显示方法
    public abstract void onShow();

    //这个是隐藏方法
    public abstract void onHide();

}
