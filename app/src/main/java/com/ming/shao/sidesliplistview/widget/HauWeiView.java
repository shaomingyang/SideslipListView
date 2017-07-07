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
 * Created by smy on 2017/6/16 0016.
 */

public class HauWeiView extends View {
    private RectF recycleRect;
    private Paint cyclePaint;
    private Paint linePaint;
    private Paint readyLinePaint;
    private float startAngle = 120;
    private float sweepAngle = 300;
    //半径
    private int radius;
    private int targetAngle = 180;

    public HauWeiView(Context context) {
        super(context);
        init(context);
    }

    public HauWeiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HauWeiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //初始化画笔
        cyclePaint = new Paint();
        cyclePaint.setColor(Color.RED);
        cyclePaint.setStrokeWidth(2);
        //让画出的图形是空心的(不填充)
        cyclePaint.setStyle(Paint.Style.STROKE);
        cyclePaint.setAntiAlias(true);
        linePaint = new Paint();
        //设置画笔颜色
        linePaint.setColor(Color.RED);
        //线宽
        linePaint.setStrokeWidth(2);
        //设置画笔抗锯齿
        linePaint.setAntiAlias(true);
        //已经处理 的颜色画笔
        readyLinePaint = new Paint();
        //设置画笔颜色
        readyLinePaint.setColor(Color.GREEN);
        //线宽
        readyLinePaint.setStrokeWidth(2);
        //设置画笔抗锯齿
        readyLinePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int reSize = Math.min(width, height);
        radius = reSize / 2;
        recycleRect = new RectF(0, 0, reSize, reSize);
        //测量出一个正方形的大小
        setMeasuredDimension(reSize, reSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆形
//        canvas.drawArc(recycleRect, startAngle, sweepAngle, false, cyclePaint);
        drawViewLine(canvas);
    }

    private void drawViewLine(Canvas canvas) {
        //先保存之前的 canvas内容
        canvas.save();
        //平移坐标至圆心位置
        canvas.translate(radius, radius);
        //将坐标旋转30°
        canvas.rotate(30);

        //确定每次旋转的角度
        float rotateAngle = sweepAngle / 99;

        int hasDraw = 0;
        for (int i = 0; i < 100; i++) {
            if (hasDraw > targetAngle && targetAngle != 0) {
                //画一条后面部分的刻度线
                canvas.drawLine(0, radius, 0, radius - 40, linePaint);
            } else {
                //画一条前面部分的刻度线
                canvas.drawLine(0, radius, 0, radius - 40, readyLinePaint);
            }
            //累计绘制过的部分
            hasDraw+=rotateAngle;
            canvas.rotate(rotateAngle);
        }

        //操作完成后恢复状态
        canvas.restore();


    }
}
