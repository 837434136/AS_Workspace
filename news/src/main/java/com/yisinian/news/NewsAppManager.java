package com.yisinian.news;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by deng on 2015/8/28.
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                             _oo0oo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            0\  =  /0
 *                          ___/`---‘\___
 *                        .' \\\|     |// '.
 *                       / \\\|||  :  |||// \\
 *                      / _ ||||| -:- |||||- \\
 *                      | |  \\\\  -  /// |   |
 *                      | \_|  ''\---/''  |_/ |
 *                      \  .-\__  '-'  __/-.  /
 *                    ___'. .'  /--.--\  '. .'___
 *                 ."" '<  '.___\_<|>_/___.' >'  "".
 *                | | : '-  \'.;'\ _ /';.'/ - ' : | |
 *                \  \ '_.   \_ __\ /__ _/   .-' /  /
 *            ====='-.____'.___ \_____/___.-'____.-'=====
 *                              '=---='
 *
 *         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                  佛祖保佑                 永无BUG
 */
public class NewsAppManager {

    private final String TAG = getClass().getSimpleName();

    private static NewsAppManager instance = null;
    //链表结构数据，适合增删改查
    private static List<Activity> mActivites = new LinkedList<Activity>();

    public NewsAppManager() {
    }

    public synchronized static NewsAppManager getInstance(){
        if(instance == null){
            instance = new NewsAppManager();
        }
        return instance;
    }

    /**
     * return mActivites size
     * @return
     */
    public int size(){return mActivites.size();}

    /**
     * get forward activity
     * @return
     */
    public synchronized Activity getForwardActivity(){
        return size() > 0 ? mActivites.get(size() - 1) : null;
    }

    /**
     * add activity
     * @param activity
     */
    public synchronized void addActivity(Activity activity){
        mActivites.add(activity);
    }

    /**
     * remove activity
     * @param activity
     */
    public synchronized void removeActivity(Activity activity){
        if (mActivites.contains(activity)){
            mActivites.remove(activity);
        }
    }

    public synchronized void clear(){
        int activitySize = mActivites.size();
        for (int i = activitySize - 1; i > -1; i--){
            Activity activity = mActivites.get(i);
            removeActivity(activity);
            activity.finish();
            //had remove one activity on mActivites
            i = activitySize;
        }
    }

    public synchronized void clearTop(){
        int activitySize = mActivites.size();
        for (int i = activitySize - 2; i > -1; i--){
            Activity activity = mActivites.get(i);
            removeActivity(activity);
            activity.finish();
            i = activitySize - 1;
        }
    }
}
