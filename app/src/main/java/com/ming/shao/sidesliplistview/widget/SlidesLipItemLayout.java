package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by smy on 2017/6/19 0019.
 * ToDO 明天继续
 */

public class SlidesLipItemLayout extends ViewGroup {
    private View contentView;
    private View rightView;
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);


    float lastX;
    float lastY;


    public SlidesLipItemLayout(Context context) {
        super(context);
        init(context);
    }

    public SlidesLipItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidesLipItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取childView的个数

        int count = getChildCount();
        //参考frameLayout测量代码
        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        //遍历childViews
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth,
                        child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight,
                        child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                childState = combineMeasuredStates(childState, child.getMeasuredState());
                if (measureMatchParentChildren) {
                    if (lp.width == LayoutParams.MATCH_PARENT ||
                            lp.height == LayoutParams.MATCH_PARENT) {
                        mMatchParentChildren.add(child);
                    }
                }
            }
        }
        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));

        count = mMatchParentChildren.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                final View child = mMatchParentChildren.get(i);
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                final int childWidthMeasureSpec;
                if (lp.width == LayoutParams.MATCH_PARENT) {
                    final int width = Math.max(0, getMeasuredWidth()
                            - lp.leftMargin - lp.rightMargin);
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                            width, MeasureSpec.EXACTLY);
                } else {
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            lp.leftMargin + lp.rightMargin,
                            lp.width);
                }

                final int childHeightMeasureSpec;
                if (lp.height == FrameLayout.LayoutParams.MATCH_PARENT) {
                    final int height = Math.max(0, getMeasuredHeight()
                            - lp.topMargin - lp.bottomMargin);
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                            height, MeasureSpec.EXACTLY);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            lp.topMargin + lp.bottomMargin,
                            lp.height);
                }

                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 0 && null == contentView) {
                contentView = getChildAt(i);
                contentView.setClickable(true);
            } else {
                rightView = getChildAt(i);
                rightView.setClickable(true);
            }
        }
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();


        MarginLayoutParams contentLp = (MarginLayoutParams) contentView.getLayoutParams();
        if (null != contentView) {
            int cLeft = left + contentLp.leftMargin;
            int cTop = top + contentLp.topMargin;
            int cRight = right + contentView.getMeasuredWidth() + contentLp.rightMargin;
            int cBottom = bottom + contentView.getMeasuredHeight() + contentLp.bottomMargin;
            contentView.layout(cLeft, cTop, cRight, cBottom);
        }
        MarginLayoutParams rightLp = (MarginLayoutParams) rightView.getLayoutParams();
        if (null != rightView) {
            int rLeft = right + contentView.getMeasuredWidth() + contentLp.rightMargin + rightLp.leftMargin;
            int rTop = rightLp.topMargin + top;
            int rRight = right + contentView.getMeasuredWidth() + contentLp.rightMargin + rLeft;
            int rBottom = bottom + rightView.getMeasuredHeight() + rightLp.bottomMargin;
            rightView.layout(rLeft, rTop, rRight, rBottom);
        }

    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 开始的点击事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float firstX = ev.getRawX();
                float firstY = ev.getRawY();
                lastX = firstX;
                lastY = firstY;


                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 拦截事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 消费事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
