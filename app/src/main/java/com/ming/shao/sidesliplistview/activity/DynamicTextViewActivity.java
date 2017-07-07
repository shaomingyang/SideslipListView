package com.ming.shao.sidesliplistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.ming.shao.sidesliplistview.R;
import com.ming.shao.sidesliplistview.widget.DynamicAddTextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by smy on 2017/6/13 0013.
 */

public class DynamicTextViewActivity extends Activity {
    //定义UPDATE_TEXT这个整型敞亮，用于表示更新TextView这个动作
    public static final int UPDATE_TEXT = 1;
    int currentProgress = 0;
    DynamicAddTextView textView;
    List<String> textList = Arrays.asList("我", "是", "程", "序", "员");
    StringBuffer stringBuffer;

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
                    String text = msg.obj.toString();
                    if (null != textView) {
                        textView.setTextContent(text);
                    }

                    break;
                default:
                    break;
            }
        }
    };

    private void initView() {
        textView = (DynamicAddTextView) this.findViewById(R.id.text_view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress < textList.size()) {
                    if (null == stringBuffer) {
                        stringBuffer = new StringBuffer();
                    }
                    try {
                        Thread.sleep(1000);
                        stringBuffer.append(textList.get(currentProgress));
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        message.obj = stringBuffer;
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
