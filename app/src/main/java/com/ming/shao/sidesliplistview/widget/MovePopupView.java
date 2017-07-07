package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by smy on 2017/6/20 0020.
 * 移动的窗口view
 */

public class MovePopupView extends View {
    //先定义方框的大小
    RectF rectF;
    //方框的画笔
    Paint squarePaint;
    //设置方框的宽100
    int squareWidth = 80;
    //设置方框的搞 50
    int squareHegiht = 50;
    //画一个在下面的三角形 三角形的底是50
    //三角形的路径
    Path triPath;
    //三角形的画笔
    Paint triPaint;
    int triWidth = 20;

    float left = 10;
    String progressText = "0%";

    float mWidth;


    //划线的paint
    Paint linePaint;

    float lineStartX;
    float linStartY;
    float lineStopX;
    float lineStopY;
    //进度条的paint
    Paint bgLinePaint;

    //文字显示
    Paint textPaint;


    public MovePopupView(Context context) {
        super(context);
        init();
    }

    public MovePopupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovePopupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //先设置方框开始的位置
        rectF = new RectF();

        squarePaint = new Paint();
        squarePaint.setColor(Color.GREEN);
        squarePaint.setAntiAlias(true);
        squarePaint.setStrokeWidth(3);

        //三角形
        triPath = new Path();

        triPaint = new Paint();
        triPaint.setColor(Color.GREEN);
        triPaint.setAntiAlias(true);
        triPaint.setStrokeWidth(3);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(10);
        linePaint.setColor(Color.GRAY);
        linePaint.setStyle(Paint.Style.FILL);

        bgLinePaint = new Paint();
        bgLinePaint.setAntiAlias(true);
        bgLinePaint.setStrokeWidth(10);
        bgLinePaint.setColor(Color.RED);
        bgLinePaint.setStyle(Paint.Style.FILL);
        //第一点
        lineStartX = (squareWidth / 2 + left);
        linStartY = 15 + squareHegiht + (triWidth / 2);

        lineStopY = 15 + squareHegiht + (triWidth / 2);

        //文字显示
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStrokeWidth(5);
        textPaint.setTextSize(20);
        textPaint.setAntiAlias(true);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);
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
            mWidth = heightMeasureSize;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //上面
        rectF.top = 10;
        //左边
        rectF.left = left;
        rectF.right = rectF.left + squareWidth;
        rectF.bottom = rectF.top + squareHegiht;


        canvas.drawRoundRect(rectF, 10, 10, squarePaint);


        triPath.reset();
        //第一个点
        float pathX = (squareWidth / 2 + left - (triWidth / 2));
        float pathY = rectF.bottom;
        triPath.moveTo(pathX, pathY);
        //第二点
        float pathLineX = (squareWidth / 2 + left);
        triPath.lineTo(pathLineX, pathY + (triWidth / 2));
        triPath.lineTo(pathX + triWidth, pathY);
        triPath.close();
        canvas.drawPath(triPath, triPaint);

        //划线
        lineStopX = getWidth() - getPaddingLeft() - (squareWidth / 2);
        canvas.drawLine(lineStartX, linStartY, lineStopX, lineStopY, linePaint);
        //画进度条的线
        canvas.drawLine(lineStartX, linStartY, pathLineX, lineStopY, bgLinePaint);
        //写字
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式

        canvas.drawText(progressText, rectF.centerX(), baseLineY, textPaint);
//        Log.e("dataeee", "lineStopX " + lineStopX + " :getWidth() " + getWidth());
    }

    public void setTextContent(float left) {
        progressText = left + "%";
        float progress = (getWidth() - getPaddingRight() - (squareWidth / 2)) - ((squareWidth / 2 + getPaddingLeft()));
//        Log.e("dataeee", " :getWidth() " + getWidth());
        this.left = left * (progress / 100) + getPaddingLeft();
        invalidate();
    }
}
