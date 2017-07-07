package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by smy on 2017/6/19 0019.
 */

public class PopupView extends View {
    RectF rectF;
    Paint paint;
    Paint textPaint;
    Path path;

    private Rect textRect = new Rect();

    public PopupView(Context context) {
        super(context);
        init();
    }

    public PopupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PopupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rectF = new RectF();
        rectF.left = 100;
        rectF.top = 10;
        rectF.right = 250;
        rectF.bottom = 100;
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);

        path = new Path();
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(20);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画矩形
        canvas.drawRoundRect(rectF, 10, 10, paint);
        //画三角形
        path.moveTo(150, 100);
        path.lineTo(175, 135);
        path.lineTo(200, 100);
        path.close();
        canvas.drawPath(path, paint);

        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式

        canvas.drawText("你好世界", rectF.centerX(), baseLineY, textPaint);
    }
}
