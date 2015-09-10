package com.yisinian.news.common;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by deng on 2015/8/28.
 * Description: user always use tools
 */
public class Constants {

        public static String SHAREDPREFERENCES = "iNews_SP";
        public static final SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyyMMdd", Locale.US);
        public static final String QQ = "qq";
        public static final String WEIXIN="weixin";
        public static final String SINA="sina";
        public static final String SETTING_STATUS="status";
        public static final String VISITOR="visitor";
        public static final String UMENGLOGIN = "com.umeng.login";
        public static final String CONTENT_TTTLE = "content_title";
        public static final String CONTENT_URL = "content_url";

}
