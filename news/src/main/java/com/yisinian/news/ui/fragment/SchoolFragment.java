package com.yisinian.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yisinian.news.R;
import com.yisinian.news.api.ApiConstants;
import com.yisinian.news.bean.Daily;
import com.yisinian.news.bean.DailyNews;
import com.yisinian.news.common.Constants;
import com.yisinian.news.ui.activity.MainActivity;
import com.yisinian.news.ui.adapter.HotAdapter;
import com.yisinian.news.utils.NewsLog;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by deng on 2015/9/1.
 */
public class SchoolFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = getClass().getSimpleName();
    private View rootView;
    private DisplayImageOptions displayImageOptions;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout schSwipeRefreshLayout;
    private RecyclerView schRecyclerview;
    private ArrayList<Daily> mList = new ArrayList<Daily>();
    private HotAdapter mAdapter;
    private String date;
    AsyncHttpClient mClient = new AsyncHttpClient();
    AsyncHttpResponseHandler mResponseHandler = new BaseJsonHttpResponseHandler<DailyNews>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, DailyNews response) {
            if (response.stories != null){
                for (Daily item : response.stories){
                    mList.add(item);
                }
                mAdapter.updateData(mList);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, DailyNews errorResponse) {
        }

        @Override
        protected DailyNews parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Gson gson = new Gson();
            return gson.fromJson(rawJsonData, DailyNews.class);
        }
    };

    public static SchoolFragment getInstance() {
        //TODO you can use bundle to transfer data
        SchoolFragment fragment = new SchoolFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_school, container,false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayImageOptions = MainActivity.instance.displayImageOptions;
        schSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.school_swipe_refresh_layout);
        schSwipeRefreshLayout.setOnRefreshListener(this);
        schSwipeRefreshLayout.setColorSchemeResources(R.color.color_primary_blue);
        initRecyclerView();
//        ImageLoader.getInstance().displayImage("http://b.hiphotos.baidu.com/image/pic/item/35a85edf8db1cb13f00ced1adf54564e92584b56.jpg", mImgSchool);
    }

    private void initRecyclerView() {
        schRecyclerview = (RecyclerView) rootView.findViewById(R.id.school_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        schRecyclerview.setHasFixedSize(true);
        schRecyclerview.setLayoutManager(mLayoutManager);

//        hotRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
//                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new HotAdapter(getActivity(), mList);
        schRecyclerview.setAdapter(mAdapter);

        //拉去知乎的故事
        Calendar dateToGetUrl = Calendar.getInstance();
        String date = Constants.simpleDateFormat.format(dateToGetUrl.getTime());
        NewsLog.i(TAG, "---> date :" + date);
        String url = ApiConstants.getDailyNewsUrl(date);
        mClient.get(getActivity(), url, mResponseHandler);
    }

    @Override
    public void onRefresh() {
    //TODO do really refresh
        schSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                schSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
