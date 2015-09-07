package com.yisinian.news.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by deng on 2015/9/7.
 */
public class Daily implements Serializable{

    @SerializedName("images")
    public List<String> images;
    @SerializedName("type")
    public int type;
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("multipic")
    public boolean multipic;
}
