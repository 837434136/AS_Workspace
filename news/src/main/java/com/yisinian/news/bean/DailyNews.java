package com.yisinian.news.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by deng on 2015/9/7.
 */
public class DailyNews implements Serializable{
    @SerializedName("date")
    public String date;
    @SerializedName("stories")
    public List<Daily> stories;

}
