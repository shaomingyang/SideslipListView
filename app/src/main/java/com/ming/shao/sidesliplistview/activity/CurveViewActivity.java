package com.ming.shao.sidesliplistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.ming.shao.sidesliplistview.R;
import com.ming.shao.sidesliplistview.widget.MovePopupView;
import com.ming.shao.sidesliplistview.widget.SafetyScoreView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by smy on 2017/6/19 0019.
 */

public class CurveViewActivity extends Activity {
    public static final int UPDATE_TEXT = 1;
    int currentProgress = 0;
    MovePopupView movePopupView;
    SafetyScoreView scoreView;
    private List<Integer> integers = Arrays.asList(80, 67, 50, 40, 85, 92, 50);
    private List<String> monthStr = Arrays.asList("01", "02", "03", "04", "05", "06", "07");
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    //在这里可以进行UI操作
                    //对msg.obj进行String强制转换
                    int text = (int) msg.obj;
                    if (null != movePopupView) {
                        movePopupView.setTextContent(text);
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
        setContentView(R.layout.activity_curve_view);
        initView();
    }

    private void initView() {
        scoreView = (SafetyScoreView) findViewById(R.id.safety_view);
        scoreView.setLinePath(monthStr, integers);
        movePopupView = (MovePopupView) findViewById(R.id.move_view_id);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress < 101) {

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
