package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by smy on 2017/6/23 0023.
 */

public class AnimationButton extends View {
    //先是建立画按钮的画笔
    Paint buttonPaint;
    //按钮的坐标
    RectF butRectF;
    //文字的画笔
    Paint textPaint;
    int circleAngle = 10;

    public AnimationButton(Context context) {
        super(context);
        init();
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //按钮画笔 设置
        buttonPaint = new Paint();
        buttonPaint = getPaint(Color.GREEN, 4, Paint.Style.FILL);

        //设置按钮的高宽
        butRectF = new RectF();

        //文字画笔设置
        textPaint = new Paint();
        textPaint = getPaint(Color.RED, 4, Paint.Style.FILL);
        textPaint.setTextSize(20);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    /**
     * 画笔的设置
     *
     * @param color
     * @param width
     * @param style
     * @return
     */
    private Paint getPaint(int color, int width, Paint.Style style) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthWeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthWeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthWeasureMode == MeasureSpec.EXACTLY) {
            width = widthWeasureSize;
        }
        if (heightMeasureMode == MeasureSpec.EXACTLY) {
            height = heightMeasureSize;
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawButton(canvas);
    }

    //画按钮
    private void drawButton(Canvas canvas) {
        butRectF.top = getPaddingTop();
        butRectF.left = getPaddingLeft();
        butRectF.right = getMeasuredWidth() + getPaddingRight();
        butRectF.bottom = getMeasuredHeight() + getPaddingBottom();
        canvas.drawRoundRect(butRectF, circleAngle, circleAngle, buttonPaint);
        //文字描述
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (butRectF.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText("点击确认按钮", butRectF.centerX(), baseLineY, textPaint);
    }

    public void setButtonRadius(int radius) {

        circleAngle = radius;
        invalidate();
    }
}
