package com.ming.shao.sidesliplistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ming.shao.sidesliplistview.R;
import com.ming.shao.sidesliplistview.widget.BezierView;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by smy on 2017/6/22 0022.
 */

public class BezierActivity extends Activity {
    BezierView bezier_view;
    private List<Float> scoreList = Arrays.asList(80.0f, 60.0f, 50.0f, 40.0f, 85.0f, 100.0f, 50.0f,10f,20f,60f,56f,86f);
    private List<String> monthStr = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        initView();
    }

    private void initView() {
        bezier_view = (BezierView) findViewById(R.id.bezier_view);

        int statusBarHeight = getStatusBarHeight();

        bezier_view.setDataList(statusBarHeight, scoreList, monthStr);
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.d(TAG, "get status bar height fail");
            e1.printStackTrace();
            return 75;
        }
    }
}
