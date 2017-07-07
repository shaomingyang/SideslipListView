package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ming.shao.sidesliplistview.R;

/**
 * Created by smy on 2017/6/12 0012.
 */

public class SimpleLineView extends View {
    private String viewText;
    private int viewBgColor;
    private Paint mBgPaint;//背景画笔
    private Paint progressPaint;//当前进度的画笔\
    private Paint textPaint;//文本显示的画笔
    RectF textRect = new RectF();
    /**
     * 当前进度
     */
    private float currentProgress;
    private float textRight;
    private float moveTextProgress;
    /**
     * 进度条画笔的宽度
     */
    private int strokeWidth;
    private int mWidth;
    private int mHeight;
    /**
     * 百分比提示框的高度
     */
    private int tipHeight;

    public SimpleLineView(Context context) {
        super(context);
        init(context);
        initPaint();
    }

    public SimpleLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initPaint();
    }

    public SimpleLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    //初始化画笔
    private void initPaint() {
        strokeWidth = dp2px(4);
        tipHeight = dp2px(40);
        currentProgress = getPaddingLeft();
        mBgPaint = getPaint(strokeWidth, Color.BLACK, Paint.Style.STROKE);
        progressPaint = getPaint(strokeWidth, Color.RED, Paint.Style.STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    /**
     * 统一处理paint
     *
     * @param strokeWidth
     * @param color
     * @param style
     * @return
     */
    private Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);
        return paint;
    }

    private void init(Context context) {
        init(context, null, -1);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SmipleLineView, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SmipleLineView_text:
                    viewText = a.getString(attr);
                    break;
                case R.styleable.SmipleLineView_lineViewBgColor:
                    viewBgColor = a.getColor(attr, Color.BLACK);
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMeasureMode, widthMeasureSize), measureHeight(heightMeasureMode, heightMeasureSize));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(getPaddingLeft(), getPaddingTop() + tipHeight, getWidth() - getPaddingRight(), getPaddingTop() + tipHeight, mBgPaint);
        canvas.drawLine(getPaddingLeft(), getPaddingTop() + tipHeight, currentProgress, getPaddingTop() + tipHeight, progressPaint);

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
            case MeasureSpec.AT_MOST://如果是没有内容view（比如説文字）设置wrap_content则宽度是0 有内容测量内容的值就是当前view的宽度
                mWidth = 0;
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
            case MeasureSpec.AT_MOST://如果是没有内容view（比如説文字）设置wrap_content则宽度是0 有内容测量内容的值就是当前view的宽度
                mHeight = dp2px(40) + getPaddingBottom() + getPaddingTop() + strokeWidth;
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height + strokeWidth;
                break;
        }
        return mHeight;
    }

    public void setCurrentProgress(int currentProgress) {//设置当前进度条
        if (currentProgress == 0) {
            this.currentProgress = getPaddingLeft();
        }
        this.currentProgress = currentProgress * (mWidth - getPaddingRight()) / 100;
        if (this.currentProgress >= getPaddingLeft()) {
            invalidate();
        }


    }
}
