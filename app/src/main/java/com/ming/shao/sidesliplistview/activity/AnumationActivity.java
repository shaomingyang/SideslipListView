package com.ming.shao.sidesliplistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.ming.shao.sidesliplistview.R;
import com.ming.shao.sidesliplistview.widget.AnimationButton;

/**
 * Created by smy on 2017/6/23 0023.
 */

public class AnumationActivity extends Activity {
    public static final int UPDATE_TEXT = 1;
    int currentProgress = 10;
    AnimationButton anima_but;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    //在这里可以进行UI操作
                    //对msg.obj进行String强制转换
                    int text = (int) msg.obj;
                    if (null != anima_but) {
                        anima_but.setButtonRadius(text);
                    }

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        initView();
    }

    private void initView() {
        anima_but = (AnimationButton) findViewById(R.id.anima_but);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress < 501) {

                    try {
                        Thread.sleep(10);

                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        message.obj = currentProgress;
                        mHandler.sendMessage(message);
                        currentProgress++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        mHandler.removeMessages(-1);
                        return;

                    }

                }
            }
        }).start();
    }
}
