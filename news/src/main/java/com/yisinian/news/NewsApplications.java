package com.yisinian.news;

import android.app.Application;
import android.view.WindowManager;

/**
 * Created by deng 2015/8/28.
 */
public class NewsApplications extends Application{

    private final String TAG = getClass().getSimpleName();
    private static NewsApplications mContext;

    /**开发者测试模式*/
    public static final boolean DEVELOPER_MODE = true;
    /**屏幕窗口管理器参数*/
    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        MobclickAgent.setDebugMode(true);
//        MobclickAgent.updateOnlineConfig(mContext);
//        MobclickAgent.openActivityDurationTrack(false);
//        UmengUpdateAgent.update(mContext);

    }

    public static NewsApplications getInstance(){
        return mContext;
    }

    public WindowManager.LayoutParams getWindowManagerParams(){
        return wmParams;
    }

    public void exitApp(){
        NewsAppManager.getInstance().clear();
        //Indicates to the VM that it would be a good time to run the garbage collector.
        System.gc();
//        MobclickAgent.onKillProcess(mContext);
        //kill the process with the given PID.
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
