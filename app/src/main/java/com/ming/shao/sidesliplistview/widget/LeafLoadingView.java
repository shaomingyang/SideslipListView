package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by smy on 2017/6/27 0027.
 */

public class LeafLoadingView extends View {
    //矩形画笔
    private Paint rectanglePaint;

    public LeafLoadingView(Context context) {
        super(context);
        init();
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        ;
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //矩形画笔
        rectanglePaint = new Paint();
        rectanglePaint.setStyle(Paint.Style.STROKE);
        rectanglePaint.setStrokeWidth(3);
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setColor(Color.YELLOW);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMeasureMode == MeasureSpec.EXACTLY) {
            width = widthMeasureSize;
        }
        if (heightMeasureMode == MeasureSpec.EXACTLY) {
            height = heightMeasureSize;
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
