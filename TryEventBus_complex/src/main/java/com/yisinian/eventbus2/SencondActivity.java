package com.yisinian.eventbus2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yisinian.eventbus2.other.FirstEvent;
import com.yisinian.eventbus2.other.SecondEvent;
import com.yisinian.eventbus2.other.ThirdEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by deng on 2015/8/31.
 */
public class SencondActivity extends AppCompatActivity{

    private Button mFirstEventBtn, mSecondEventBtn, mThirdEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mFirstEventBtn = (Button) findViewById(R.id.first_event_btn);
        mSecondEventBtn = (Button) findViewById(R.id.second_event_btn);
        mThirdEventBtn = (Button) findViewById(R.id.third_event_btn);

        mFirstEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated constructor stub
                EventBus.getDefault().post(new FirstEvent("FirstEvent btn clicked"));
            }
        });

        mSecondEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Auto-generated constructor stub
                EventBus.getDefault().post(new SecondEvent("SecondEvent btn clicked"));
            }
        });

        mThirdEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Auto-generated constructor stub
                EventBus.getDefault().post(new ThirdEvent("ThirdEvent btn clicked"));
            }
        });

    }
}
