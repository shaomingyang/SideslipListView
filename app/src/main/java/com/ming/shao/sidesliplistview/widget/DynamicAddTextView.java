package com.ming.shao.sidesliplistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by smy on 2017/6/13 0013.
 */

public class DynamicAddTextView extends AppCompatTextView {
    private String textContent;
    private Rect textRect = new Rect();

    public DynamicAddTextView(Context context) {
        super(context);
    }

    public DynamicAddTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicAddTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(textContent)) {
            textRect.left = getPaddingLeft();
            textRect.top = getPaddingTop();
            textRect.right = getWidth() - getPaddingRight();
            textRect.bottom = getHeight() - getPaddingBottom();
            Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
            int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            canvas.drawText(textContent, getPaddingLeft(), baseline, getPaint());
        }

    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
        invalidate();
    }
}
