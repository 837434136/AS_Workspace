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
    public static final class Urls{

    }

    /**
     * folder paths
     */
    public static final class Paths{
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "/News/Images/";
    }
}
