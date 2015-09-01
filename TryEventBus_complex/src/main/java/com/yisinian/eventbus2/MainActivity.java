package com.yisinian.eventbus2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yisinian.eventbus2.other.FirstEvent;
import com.yisinian.eventbus2.other.SecondEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by deng on 2015/8/31
 * 当发送SecondEvent实例的消息过来的时候，这三个函数会同时接收到并各自执行，所以当点击Second Event这个button的时候，会出现下面的顺序结果：
 *09-01 11:21:42.340  13711-13711/com.yisinian.eventbus2 D/MainActivity﹕ OnEvent收到了消息：MainEvent:SecondEvent btn clicked
 *09-01 11:21:42.340  13711-13711/com.yisinian.eventbus2 D/MainActivity﹕ onEventMainThread 收到了消息：MainEvent:SecondEvent btn clicked
 *09-01 11:21:42.340  13711-17374/com.yisinian.eventbus2 D/MainActivity﹕ onEventAsync收到了消息：MainEvent:SecondEvent btn clicked
 *09-01 11:21:42.341  13711-17373/com.yisinian.eventbus2 D/MainActivity﹕ onEventBackgroundThread 收到了消息：MainEvent:SecondEvent btn clicked
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private Button btn;
    private TextView tv;
//    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册EventBus消息接收器
        EventBus.getDefault().register(this);
        Log.e(TAG, "MainActivity oncreate EventBus register");
        btn = (Button) findViewById(R.id.complex_try_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated constructor stub
                MainActivity.this.startActivity(new Intent(MainActivity.this, SencondActivity.class));
            }
        });
    }

    public void onEventMainThread(FirstEvent event){
        Log.d(TAG, "onEventMainThread 收到了消息：" + event.getMsg());
    }

    //SecondEvent接收函数一
    public void onEventMainThread(SecondEvent event){
        Log.d(TAG, "onEventMainThread 收到了消息：" + event.getMsg());
    }

    //SecondEvent接收函数三
    public void onEventBackgroundThread(SecondEvent event){
        Log.d(TAG, "onEventBackgroundThread 收到了消息：" + event.getMsg());
    }

    //SecondEvent接收函数三
    public void onEventAsync(SecondEvent event){
        Log.d(TAG, "onEventAsync收到了消息：" + event.getMsg());
    }

    public void onEvent(SecondEvent event){
        Log.d(TAG, "OnEvent收到了消息：" + event.getMsg());
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated constructor stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
