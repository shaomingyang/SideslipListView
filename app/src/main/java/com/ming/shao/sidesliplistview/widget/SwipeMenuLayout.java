package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.ming.shao.sidesliplistview.State;

import java.util.ArrayList;

/**
 * Created by smy on 2017/6/14 0014.
 */

public class SwipeMenuLayout extends ViewGroup {
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);
    private View contentView;
    private View rightView;
    private MarginLayoutParams mMarginViewLp;
    //判断是否是滑动的最小值
    private int mScaledTouchSlop;
    private Scroller mScroller;
    private static SwipeMenuLayout mViewCache;
    private static State mStateCache;

    public SwipeMenuLayout(Context context) {
        super(context);
        init(context);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //创建辅助对象
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        mScroller = new Scroller(context);
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
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (contentView == null && i == 0) {
                contentView = child;
                contentView.setClickable(true);
            } else {
                rightView = child;
                rightView.setClickable(true);
            }
        }
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        if (null != contentView) {
            mMarginViewLp = (MarginLayoutParams) contentView.getLayoutParams();
            int cLeft = left + mMarginViewLp.leftMargin;
            int cTop = top + mMarginViewLp.topMargin;
            int cRight = cLeft + contentView.getMeasuredWidth();
            int cBottom = cTop + contentView.getMeasuredHeight();
            contentView.layout(cLeft, cTop, cRight, cBottom);
        }
        if (null != rightView) {
            MarginLayoutParams mMarginleftViewLp = (MarginLayoutParams) rightView.getLayoutParams();
            int rLeft = left + contentView.getMeasuredWidth() + right;
            int rTop = top + mMarginleftViewLp.topMargin;
            int rRight = rLeft + rightView.getMeasuredWidth();
            int rBottom = rTop + rightView.getMeasuredHeight();
            rightView.layout(rLeft, rTop, rRight, rBottom);
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    float firstX;
    float firstY;
    float lastX;
    float lastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                firstX = ev.getRawX();
                firstY = ev.getRawY();
                lastX = firstX;
                lastY = firstY;
                Log.e("ACTION_DOWNdistanceY", "=========" + lastY);
                if (mViewCache != null) {
                    if (mViewCache != this) {
                        mViewCache.handlerSwipeMenu(State.CLOSE);

                    }
//                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                float distanceX = lastX - event.getRawX();
                float distanceY = lastY - event.getRawY();
                Log.e("ACTION_MOVEdistanceY", "=========" + distanceY);
                Log.e("ACTION_MOVEgetScrollX()", "=========" + getScrollX());
                Log.e("ACTION_MOVEViewWidth()", "=========" + rightView.getMeasuredWidth());
                //当处于水平滑动时，禁止父类拦截
                if (Math.abs(distanceX) > mScaledTouchSlop * 2 || Math.abs(getScrollX()) > mScaledTouchSlop * 2) {
                    requestDisallowInterceptTouchEvent(true);
                }
                //如果是横着滑动
                if (Math.abs(distanceY) > mScaledTouchSlop * 2) {
                    requestDisallowInterceptTouchEvent(true);
                }
                scrollBy((int) (distanceX), 0);//滑动使用scrollBy

                //越界修正 getScrollX()就是滑动的距离
                if (getScrollX() < 0) {
                    scrollTo(0, 0);
                } else if (getScrollX() > rightView.getMeasuredWidth()) {
                    scrollTo(rightView.getMeasuredWidth(), 0);
                }


                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                Log.e("onTouchEventACTION_UP", "=========" + getScrollX());

                if (getScrollX() > (rightView.getMeasuredWidth() / 2)) {
                    mScroller.startScroll(getScrollX(), 0, rightView.getMeasuredWidth() - getScrollX(), 0);
                } else {
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
                }
                State result = isShouldOpen(getScrollX());
                handlerSwipeMenu(result);
                break;
            case MotionEvent.ACTION_CANCEL: {
                State resultCANCEL = isShouldOpen(getScrollX());
                handlerSwipeMenu(resultCANCEL);
                break;
            }
        }
        return super.onTouchEvent(event);
    }
    /**
     * 根据当前的scrollX的值判断松开手后应处于何种状态
     *
     * @param scrollX
     * @return
     */
    private State isShouldOpen(int scrollX) {
        if (getScrollX() > 0 && rightView != null) {
            //⬅️滑动
            if (Math.abs(rightView.getWidth() * 0.5f) < Math.abs(getScrollX())) {
                return State.OPEN;
            }

        }
        return State.CLOSE;
    }
    /**
     * 先将滑动事件进行拦截
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //对左边界进行处理
                float distance = lastX - event.getRawX();
                if (Math.abs(distance) > mScaledTouchSlop) {
                    // 当手指拖动值大于mScaledTouchSlop值时，认为应该进行滚动，拦截子控件的事件
                    return true;
                }
                break;

            }

        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断Scroller是否执行完毕：
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通知View重绘-invalidate()->onDraw()->computeScroll()
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this == mViewCache) {
            mViewCache.handlerSwipeMenu(State.CLOSE);
        }

        //  Log.i(TAG, ">>>>>>>>onDetachedFromWindow");

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (this == mViewCache) {
            mViewCache.handlerSwipeMenu(mStateCache);
        }
    }

    /**
     * 设置当前状态
     *
     * @param result
     */
    private void handlerSwipeMenu(State result) {
        if (result == State.OPEN) {//打开状态
            mScroller.startScroll(getScrollX(), 0, rightView.getMeasuredWidth() - getScrollX(), 0);
            mViewCache = this;
            mStateCache = result;
        } else if (result == State.DRAFING) {//其他状态
            mViewCache = this;
            mStateCache = result;
        } else {//关闭状态
            mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
            mViewCache = null;
            mStateCache = null;
        }
        invalidate();
    }
}
