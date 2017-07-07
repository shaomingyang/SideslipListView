package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ming.shao.sidesliplistview.ProjectApp;

import java.util.Arrays;
import java.util.List;

/**
 * Created by smy on 2017/6/19 0019.
 */

public class CurveView extends View {
    private Paint mPaint;
    private Paint textPaint;
    private Path mPath;// 路径对象
    private List<Integer> integers = Arrays.asList(70, 60, 80, 90, 45, 62, 70, 99);
    int with;
    int height;

    public CurveView(Context context) {
        super(context);
        init(context);
    }

    public CurveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(Color.GREEN);
        //线宽
        mPaint.setStrokeWidth(2);
        //设置画笔抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        textPaint = new Paint();
        //设置画笔颜色
        textPaint.setColor(Color.GREEN);
        //线宽
        textPaint.setStrokeWidth(1);
        textPaint.setTextSize(25); //以px为单位
        // 实例化路径
        mPath = new Path();
        //获取屏幕的宽度 除以8 获取当前屏幕一点的大小
        with = ProjectApp.screenWidth / 8;
        height = ProjectApp.screenHeight / 3;


        // 定义路径的起点
        mPath.moveTo(with, integers.get(0) * (height / 100));
        for (int i = 0; i < 8; i++) {
            mPath.lineTo(with * (i + 1), integers.get(i) * (height / 100));
            Log.e("data", "with:   " + with * (i + 1) + "   height: " + integers.get(i) * (height / 100));
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
         /*
         * 绘制路径
         */
//        //平移画布将画布的点移动到屏幕的1/3处
//        canvas.translate(0, height);
//        //将画布负方向旋转90° （逆时针）
//        canvas.rotate(-90);
        mPaint.setPathEffect(new CornerPathEffect(50));
        canvas.drawPath(mPath, mPaint);
        for (int i = 0; i < 8; i++) {
            canvas.drawText("0" + (i + 1), with * (i + 1), height + 20, textPaint);
            Log.e("data", "with:   " + with * (i + 1) + "   height: " + integers.get(i) * (height / 100));
        }

    }
}
