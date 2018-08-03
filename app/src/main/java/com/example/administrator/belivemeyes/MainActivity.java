package com.example.administrator.belivemeyes;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mText;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            mText.setText(what+"");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.daojishi);

        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i =5; i >= 0; i--) {
                    Message message = new Message();
                    message.what = i;
                    try {
                        Thread.sleep(1000);
                        handler.sendMessage(message);
                        if (i == 0){
                            Intent intent = new Intent(MainActivity.this,StyleActivity.class);//跳转界面
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


}
