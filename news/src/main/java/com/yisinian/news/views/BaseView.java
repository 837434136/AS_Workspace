package com.yisinian.news.views;

/**
 * Created by deng on 2015/8/28.
 */
public interface BaseView {

    /**
     * show message error
     * @param msg
     */
    void showError(String msg);

    /**
     * show message exception
     * @param msg
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNewError();

    /**
     * show loading message
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();
}


