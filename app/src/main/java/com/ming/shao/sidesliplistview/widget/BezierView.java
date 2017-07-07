package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ming.shao.sidesliplistview.ProjectApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/6/22 0022.
 */

public class BezierView extends View {
    private Path mPath;
    private Paint bePaint;
    //底下日期显示的画笔
    Paint datePaint;

    private List<Point> mPoints = new ArrayList<>();
    /**
     * 中点集合
     */
    private List<Point> mMidPoints = new ArrayList<>();
    /**
     * 中点的中点集合
     */
    private List<Point> mMidMidPoints = new ArrayList<>();
    /**
     * 移动后的点集合(控制点)
     */
    private List<Point> mControlPoints = new ArrayList<>();

    private List<Float> scoreList = new ArrayList<>();
    private List<String> monthStr = new ArrayList<>();
    float withPoint;
    float heightPoint;
    //圆形坐标
    float lineStartX;
    float lineStartY;
    //圆圈的画笔
    Paint circlePaint;
    int statusBarHeight;

    //中间的线
    Paint linePaint;
    float textLinewidth;

    float scrollX;
    float mXMove;
    float mXLastMove;


    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;
    /**
     * 界面可滚动的右边界
     */
    private float rightBorder;

    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();

        bePaint = new Paint();
        bePaint.setAntiAlias(true);
        bePaint.setStrokeWidth(5);
        bePaint.setColor(Color.RED);
        bePaint.setStyle(Paint.Style.STROKE);

        //获取屏幕的宽度 除以8 获取当前屏幕一点的大小
        withPoint = ProjectApp.screenWidth / 8;


        //圆圈
        circlePaint = new Paint();
        circlePaint.setStrokeWidth(2);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.FILL);
        //画底下月份
        datePaint = new Paint();
        datePaint = new Paint();
        datePaint.setStrokeWidth(3);
        datePaint.setTextSize(20);
        datePaint.setColor(Color.BLACK);
        datePaint.setAntiAlias(true);


        //画中间的线
        linePaint = new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.GREEN);

    }

    private void initPoint(List<Float> scoreList) {
        for (int i = 0; i < scoreList.size(); i++) {
            //获取字符串在屏幕上的宽度
            textLinewidth = linePaint.measureText(monthStr.get(i));
            //根据手机的坐标去  y轴是向下是正方向 所以 以当前的数值用（100-y）
            float pointY = (100 - scoreList.get(i)) * (heightPoint / 100) + statusBarHeight;
            float pointX = withPoint * (i + 1) + textLinewidth / 2;

            Point point = new Point();
            point.x = pointX;
            point.y = pointY;
            mPoints.add(point);//存储曲线经过的点的坐标
            if (i == 3) {
                lineStartX = pointX;
                lineStartY = pointY;
            }
        }
        if (scoreList.size() > 8) {
            rightBorder = withPoint * (scoreList.size() + 1);
        } else {
            rightBorder = ProjectApp.screenWidth;
        }

    }

    /**
     * 初始化中点集合
     */
    private void initMidPoints(List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            Point midPoint = null;
            if (i == points.size() - 1) {//等于最后一个点返回
                return;
            } else {
                float midPointX = (points.get(i).x + points.get(i + 1).x) / 2;//获取现在点和后面的点的坐标 除以二
                float midPointY = (points.get(i).y + points.get(i + 1).y) / 2;
                midPoint = new Point();
                midPoint.x = midPointX;
                midPoint.y = midPointY;
            }
            mMidPoints.add(midPoint);
        }
    }

    /**
     * 初始化中点的中点集合
     */
    private void initMidMidPoints(List<Point> mMidPoints) {
        for (int i = 0; i < mMidPoints.size(); i++) {
            Point midMidPoint = null;
            if (i == mMidPoints.size() - 1) {
                return;
            } else {
                float midmidPointX = (mMidPoints.get(i).x + mMidPoints.get(i + 1).x) / 2;
                float midMidPointY = (mMidPoints.get(i).y + mMidPoints.get(i + 1).y) / 2;
                midMidPoint = new Point();
                midMidPoint.x = midmidPointX;
                midMidPoint.y = midMidPointY;
            }
            mMidMidPoints.add(midMidPoint);
        }

    }

    /**
     * 初始化控制点集合
     */
    private void initControlPoints(List<Point> points, List<Point> midPoints, List<Point> midMidPoints) {
        for (int i = 0; i < points.size(); i++) {
            if (i == 0 || i == points.size() - 1) {
                continue;
            } else {

                Point before = new Point();
                Point after = new Point();
                before.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i - 1).x;
                before.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i - 1).y;
                after.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i).x;
                after.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i).y;
                mControlPoints.add(before);
                mControlPoints.add(after);
            }
        }

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
//        canvas.drawPath(path, bePaint);


        // 画贝塞尔曲线
        drawBezier(canvas);

        //画月份
        drawMonth(canvas);
        //画一个圆
        canvas.drawCircle(lineStartX, lineStartY, 8, circlePaint);
        //画垂直的线
        //加10目的是为了有点间距
        canvas.drawLine(lineStartX, lineStartY + 10, lineStartX, heightPoint - 10, linePaint);
//        // 画穿越原始点的折线
//        drawCrossPointsBrokenLine(canvas);
    }

    /**
     * 画穿越原始点的折线
     */
    private void drawCrossPointsBrokenLine(Canvas canvas) {
        linePaint.setStrokeWidth(3);
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        // 重置路径
        mPath.reset();
        // 画穿越原始点的折线
        mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
        for (int i = 0; i < mPoints.size(); i++) {
            mPath.lineTo(mPoints.get(i).x, mPoints.get(i).y);
        }
        canvas.drawPath(mPath, linePaint);
    }

    /**
     * 画贝塞尔曲线
     *
     * @param canvas
     */
    private void drawBezier(Canvas canvas) {
        mPath.reset();
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == 0) {// 第一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(i).x, mControlPoints.get(i).y,// 控制点
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);
            } else if (i < mPoints.size() - 2) {// 三阶贝塞尔
                mPath.cubicTo(mControlPoints.get(2 * i - 1).x, mControlPoints.get(2 * i - 1).y,// 控制点
                        mControlPoints.get(2 * i).x, mControlPoints.get(2 * i).y,// 控制点
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);// 终点
            } else if (i == mPoints.size() - 2) {// 最后一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(mControlPoints.size() - 1).x, mControlPoints.get(mControlPoints.size() - 1).y,
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);// 终点
            }
        }
        canvas.drawPath(mPath, bePaint);
    }

    /**
     * 画下面的月份
     *
     * @param canvas
     */
    private void drawMonth(Canvas canvas) {
        if (null != monthStr && !monthStr.isEmpty()) {
            for (int i = 0; i < monthStr.size(); i++) {
                canvas.drawText(monthStr.get(i), withPoint * (i + 1), heightPoint + 40, datePaint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXLastMove = event.getRawX();

                float x = event.getX() + scrollX;
                Log.e("data", "x: " + x + "  lastX: " + scrollX);
                int i = (int) (x / (withPoint));
                Log.e("data", "i: " + i);
                if (i < scoreList.size()) {
                    //获取字符串在屏幕上的宽度
                    textLinewidth = linePaint.measureText(monthStr.get(i));
                    lineStartY = (100 - scoreList.get(i)) * (heightPoint / 100) + statusBarHeight;//圆 的坐标
                    lineStartX = withPoint * (i + 1) + textLinewidth / 2;
                }
                Log.e("data", "lineStartY: " + lineStartY + " lineStartX: " + lineStartX);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                leftBorder = getLeft();
                scrollX = getScrollX();
                mXMove = event.getRawX();
                int distanceX = (int) (mXLastMove - mXMove);
                if (getScrollX() + distanceX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + distanceX > rightBorder) {
                    scrollTo((int) rightBorder - getWidth(), 0);
                    return true;
                }

                scrollBy(distanceX, 0);
                mXLastMove = mXMove;
                break;

        }
        return true;
    }

    public void setDataList(int statusBarHeight, List<Float> scoreList, List<String> monthStr) {
        this.scoreList = scoreList;
        this.monthStr = monthStr;
        this.statusBarHeight = statusBarHeight;
        heightPoint = ProjectApp.screenHeight / 3;
        initPoint(this.scoreList);
        //** 初始化中点集合 */
        initMidPoints(mPoints);
        initMidMidPoints(mMidPoints);

        /** 初始化控制点集合 */
        initControlPoints(mPoints, mMidPoints, mMidMidPoints);
    }
}

