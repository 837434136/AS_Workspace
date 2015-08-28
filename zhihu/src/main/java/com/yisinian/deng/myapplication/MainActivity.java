package com.yisinian.deng.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by deng on 2015/8/26.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageButton imgBtn;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initToolbar();
        imgBtn = (ImageButton) findViewById(R.id.fabButton);
        initRecyclerView();

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //这里需要一个onScrollListener
        recyclerView.addOnScrollListener(new RecyclerScrollListener() {
            @Override
            public void onShow() {
                hideView();
            }

            @Override
            public void onHide() {

                showView();
            }
        });

        //use a fragment layout manager
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        // specify an adapter (see also next example)
        mList = getData();
        mAdapter = new RecycleAdapter(mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void showView() {
        toolbar.animate().translationY(-toolbar.getHeight()).setDuration(500).setInterpolator(new AccelerateInterpolator(2));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imgBtn.getLayoutParams();
        int bottom = lp.bottomMargin;
        imgBtn.animate().translationY(imgBtn.getHeight() + bottom).setDuration(500).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void hideView() {
        toolbar.animate().translationY(0).setDuration(500).setInterpolator(new AccelerateInterpolator(2));
        imgBtn.animate().translationY(0).setDuration(500).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void initToolbar(){
        getSupportActionBar();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.toolbar_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
//        toolbar.setBackgroundResource(R.color.color_primary_blue);
        setSupportActionBar(toolbar);
    }

    private ArrayList<String> getData(){
//        if (mList != null){
//            mList.clear();
//        }
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i< 20; i++){
            data.add("item" + i);
        }
        return data;
    }

}
