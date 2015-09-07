package com.yisinian.news.api;

import android.os.Environment;

/**
 * Created by deng on 2015/8/28.
 * Description: online request class or method
 */
public class ApiConstants {

    /**
     * urls address
     */
    public static final String ZHIHU_DAILT_NEWS = "http://news.at.zhihu.com/api/4/news/before/";
    public static final String ZHIHU_DAILY_NEWS_CONTENT = "http://daily.zhihu.com/story/";

    /**
     * folder paths
     */
    public static final class Paths{
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "/News/Images/";
    }

    public static String getDailyNewsUrl(String date){
        return ZHIHU_DAILT_NEWS + date;
    }

    public static String getDailyNewsContentUrl(int id){
        return ZHIHU_DAILY_NEWS_CONTENT + id;
    }

}
