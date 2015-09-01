package com.yisinian.eventbus2.other;

/**
 * Created by deng on 2015/8/31.
 */
public class SecondEvent {

    private String mMsg;

    public SecondEvent(String msg) {
        // TODO Auto-generated constructor stub
        mMsg = "MainEvent:" + msg;
    }

    public String getMsg(){
        return mMsg;
    }
}
