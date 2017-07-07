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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/6/21 0021.
 */

public class SafetyScoreView extends View {
    //曲线的画笔
    Paint curvePaint;
    //线路点
    Path linePath;
    //圆圈的画笔
    Paint circlePaint;

    //底下日期显示的画笔
    Paint datePaint;

    int withPoint;
    int heightPoint;
    //中间的线
    Paint linePaint;
    float lineStartX;
    float lineStartY;

    float lineLastX;
    float lineLastY;

    private List<String> monthStr = new ArrayList<>();
    String mo = "07";

    public SafetyScoreView(Context context) {
        super(context);
        init();
    }

    public SafetyScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SafetyScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //曲线画笔
        curvePaint = new Paint();
        curvePaint.setStrokeWidth(3);
        curvePaint.setColor(Color.GREEN);
        curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setAntiAlias(true);

        //线路点
        linePath = new Path();


        //日期的画笔
        datePaint = new Paint();
        datePaint.setStrokeWidth(3);
        datePaint.setTextSize(20);
        datePaint.setColor(Color.BLACK);
        datePaint.setAntiAlias(true);

        //获取屏幕的宽度 除以8 获取当前屏幕一点的大小
        withPoint = ProjectApp.screenWidth / 8;
        heightPoint = ProjectApp.screenHeight / 3;

        linePaint = new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.GREEN);

        //圆圈
        circlePaint = new Paint();
        circlePaint.setStrokeWidth(2);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMesureSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMesureMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;

        if (widthMeasureMode == MeasureSpec.EXACTLY) {

            width = widthMeasureSize;
        }
        if (heightMesureMode == MeasureSpec.EXACTLY) {
            height = heightMesureSize;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        curvePaint.setPathEffect(new CornerPathEffect(10));
        canvas.drawPath(linePath, curvePaint);
        if (null != monthStr && !monthStr.isEmpty()) {
            for (int i = 0; i < monthStr.size(); i++) {
                canvas.drawText(monthStr.get(i), withPoint * (i + 1), heightPoint + 20, datePaint);
                if (i == 3) {
                    //获取字符串在屏幕上的宽度
                    float width = linePaint.measureText(monthStr.get(i));
                    //画一个圆
                    canvas.drawCircle(lineStartX + width / 2, lineStartY, 8, circlePaint);
                    Log.e("dataee", "drawCircle:  " + lineStartY);
                    //加10目的是为了有点间距
                    canvas.drawLine(lineStartX + width / 2, lineStartY+10, withPoint * (i + 1) + width / 2, heightPoint - 10, linePaint);
                    canvas.drawText("40分", withPoint * (i + 1) - width / 2, lineStartY - 70, datePaint);
                }
            }

        }
    }

    //设置当前月和 当前月的成绩
    public void setLinePath(List<String> month, List<Integer> pathPoint) {
        monthStr = month;
        for (int i = 0; i < pathPoint.size(); i++) {
            //根据手机的坐标去  y轴是向下是正方向 所以 以当前的数值用（100-y）
            float pointY = (100 - pathPoint.get(i)) * (heightPoint / 100);
            if (i == 0) {
                linePath.moveTo(withPoint, pointY);
                lineLastX = withPoint;

            } else {
                if (i == 3) {
                    lineStartX = withPoint * (i + 1);
                    lineStartY = pointY;
                }
//                linePath.lineTo(withPoint * (i + 1), pointY);
                 //贝塞尔曲线
                linePath.quadTo(lineLastX, pointY, lineLastX, lineLastY);

//                float mControlX1
//
//                linePath.cubicTo(mControlX1, mControlY1, mControlX2, mControlY2, mEndX, mEndY); //三阶贝塞尔曲线，参数是控制点1、控制点2和终点坐标
                lineLastX = withPoint * (i + 1);
            }
            lineLastY = pointY;
//            Log.e("data", "with:   " + with * (i + 1) + "   height: " + integers.get(i) * (height / 100));
        }
        invalidate();

    }
}
