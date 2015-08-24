package com.yisinian.deng.as_workspace;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

//Handler 处理机制详细分析
public class MainActivity extends Activity {

    private TextView textView;
    private ImageView imageView;
    private android.os.Handler handler = new android.os.Handler();
    private int images[] = {R.drawable.image1,R.drawable.image2, R.drawable.image3, R.drawable.image4};
    private int index;
    private MyRunnable myRunnable = new MyRunnable();

    class  MyRunnable implements Runnable{
        @Override
        public void run() {
            index++;
            index = index%4;
            imageView.setImageResource(images[index]);
            handler.postDelayed(myRunnable,1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        imageView = (ImageView) findViewById(R.id.imageView);
        handler.postDelayed(myRunnable,1000);

        /*new Thread(){
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("update textview");
                        }
                    });

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();*/

    }

}
