package com.ming.shao.sidesliplistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.ming.shao.sidesliplistview.R;
import com.ming.shao.sidesliplistview.widget.SimpleLineView;

/**
 * Created by smy on 2017/6/12 0012.
 */

public class SimpleLineActivity extends Activity {
    //定义UPDATE_TEXT这个整型敞亮，用于表示更新TextView这个动作
    public static final int UPDATE_TEXT = 1;
    SimpleLineView simLine;
    int currentProgress = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_line);
        initView();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    //在这里可以进行UI操作
                    //对msg.obj进行String强制转换
                    int currentProgress = (int) msg.obj;
                    if (null != simLine) {
                        simLine.setCurrentProgress(currentProgress);
                    }

                    break;
                default:
                    break;
            }
        }
    };

    private void initView() {
        simLine = (SimpleLineView) this.findViewById(R.id.simLine);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress <= 100) {
                    try {
                        Thread.sleep(100);
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
