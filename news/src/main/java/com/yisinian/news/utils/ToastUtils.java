package com.yisinian.news.utils;

import android.content.Context;
import android.widget.Toast;

import com.yisinian.news.NewsApplications;

/**
 * Created by deng on 2015/8/28.
 */
public class ToastUtils {

    public static Toast toast = null;

    private ToastUtils() {
    }

    private static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    private static void show(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showShort(int resId) {
        Toast.makeText(NewsApplications.getInstance(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(NewsApplications.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(NewsApplications.getInstance(), resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(NewsApplications.getInstance(), message, Toast.LENGTH_LONG).show();
    }

}
