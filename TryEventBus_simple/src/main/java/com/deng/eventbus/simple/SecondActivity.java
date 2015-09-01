package com.deng.eventbus.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import de.greenrobot.event.EventBus;

/**
 * Created by java on 2015/8/31.
 */
public class SecondActivity extends AppCompatActivity{

    private Button mFirstEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mFirstEventBtn = (Button) findViewById(R.id.first_event_btn);
        mFirstEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(
                        new FirstEvent("FirstEvent btn clicked")
                );
            }
        });
    }
}
