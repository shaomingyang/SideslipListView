package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by smy on 2017/6/9 0009.
 */

public class HorizontalProgressBar extends View {
    //背景画笔
    private Paint bgPaint;
    /**
     * 进度条背景颜色
     */
    private int bgColor = 0xFFf66b12;
    private int mWidth;
    private int mHeight;
    private int mViewHeight;
    public HorizontalProgressBar(Context context) {
        super(context);
        init();
        initPaint();
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initPaint();
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initPaint();
    }

    private void init() {

    }

    /**
     *初始化画笔
     */
    private void initPaint() {
        bgPaint = getPaint(dp2px(4), bgColor, Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height));
    }
    /**
     * 测量宽度
     *
     * @param mode
     * @param width
     * @return
     */
    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth = width;
                break;
        }
        return mWidth;
    }

    /**
     * 测量高度
     *
     * @param mode
     * @param height
     * @return
     */
    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                mHeight = mViewHeight;
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
        }
        return mHeight;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画一条线的背景
        canvas.drawLine(getPaddingLeft(), dp2px(8), getWidth(), dp2px(8), bgPaint);
    }

    /**
     * 统一处理paint
     */
    private Paint getPaint(int strokeWidth, int color, Paint.Style style){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);
        return paint;

    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
