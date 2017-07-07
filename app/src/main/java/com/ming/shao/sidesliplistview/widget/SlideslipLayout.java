package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by smy on 2017/6/8 0008.
 */

public class SlideslipLayout extends ViewGroup {
    private static final String TAG = "SlideslipLayout";

    private int mScaledTouchSlop;
    private Scroller mScroller;


    public SlideslipLayout(Context context) {
        super(context);
        init(context);
    }

    public SlideslipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideslipLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //创建辅助对象 包含了方法和标准的常量用来设置UI的超时、大小和距离
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        //流畅滑动
        mScroller = new Scroller(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            int with = 0;
            int preWith = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 为ScrollerLayout中的每一个子控件在水平方向上进行布局
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
//                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
                preWith = with;
                with = with + childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                childView.layout(preWith, 0, with, childView.getMeasuredHeight());

            }
            // 初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;
    private float mXLastMove;
    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;
    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TouchEvent", "ACTION_DOWN");
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                int scrolledX = (int) (mXLastMove - mXMove);
                Log.e("TouchEvent", "HorizontalTextView------------onTouchEvent  leftBorder:" + leftBorder + " rightBorder:" + rightBorder + " scrolledX:" + scrolledX + "  mXLastMove:" + mXLastMove + " mXMove:" + mXMove + "  getScrollX():" + getScrollX());
                if (getScrollX() + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);
//                    Log.e("TouchEvent", "ScrollerLayout------------onTouchEvent返回 1 true" + "  getScrollX;" + getScrollX() + " getWidth:" + getWidth() + " scrolledX:" + scrolledX + " leftBorder:" + leftBorder);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
//                    Log.e("TouchEvent", "ScrollerLayout------------onTouchEvent返回 2 true" + "  getScrollX;" + getScrollX() + " getWidth:" + getWidth() + " scrolledX:" + scrolledX + " leftBorder:" + leftBorder);
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
//                Log.e("TouchEvent", "ScrollerLayout------------onTouchEvent返回 super   scrolledX:" + scrolledX);
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;

                break;
            case MotionEvent.ACTION_UP:
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
//                Log.e("TouchEvent", "ScrollerLayout------------onTouchEvent返回 ACTION_UP" + "  getScrollX;" + getScrollX() + " getWidth:" + getWidth() + " targetIndex:" + targetIndex + "  dx:" + dx);
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面

                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;

        }
        return true;
    }

    /**
     * 重写分发点击事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("dispatchTouchEvent", "ACTION_DOWN::  ");
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("dispatchTouchEvent", "ACTION_MOVE::  ");
                break;
            case MotionEvent.ACTION_UP:
//                Log.e("dispatchTouchEvent", "ACTION_UP::  ");
                break;
        }
        return super.dispatchTouchEvent(ev);

    }

    // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
    @Override
    public void computeScroll() {
//        Log.e("TouchEvent", "重写computeScroll---重写computeScroll()方法，并在其内部完成平滑滚动的逻辑");
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Log.d(TAG, "dispatchTouchEvent() called with: " + "ev = [" + event + "]");

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("onInterceptTouchEvent", "ACTION_DOWN::  ");
                break;
            case MotionEvent.ACTION_MOVE:
                //对左边界进行处理
                float distance = mXLastMove - event.getRawX();
//                Log.e("onInterceptTouchEvent", "distance::  " + distance);
                if (Math.abs(distance) > mScaledTouchSlop) {
                    // 当手指拖动值大于mScaledTouchSlop值时，认为应该进行滚动，拦截子控件的事件
                    return true;
                }
                break;


        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
