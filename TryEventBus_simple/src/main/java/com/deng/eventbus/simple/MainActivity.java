package com.deng.eventbus.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册接收消息的页面
        EventBus.getDefault().register(this);

        btn = (Button) findViewById(R.id.try_btn);
        tv = (TextView) findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

    }

    //在主线程中接收消息，可以更新UI界面
    public void onEventMainThread(FirstEvent event){

        String msg = "onEventMainThread收到消息： " + event.getMsg();
        Log.d(TAG, "" + msg);
        Log.d(TAG,"" + Thread.currentThread().getId());
        btn.setText("我是主线程小贱人...");
        if (!TextUtils.isEmpty(msg)){
            tv.setText(msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    //在对应发送消息的线程中接收消息，需要更新UI组件的话要分清是主线程还是子线程发送过来的消息
    public void onEvent(FirstEvent event){
//        btn.setText("我是当前线程小贱人...");
        Log.d(TAG, "onEvent 收到消息" + event.getMsg());
        Log.d(TAG,"" + Thread.currentThread().getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);
    }
}
