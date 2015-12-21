package com.example.lsj.workspace;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/5/6.
 * 圆形的TextView
 */
public class MyCircleTextView extends View {
    private int mLabelTextColor;
    private float mLabelTextSize;
    private int mLabelColor;
    private String mLabelText;
    private TransformationMethod transformationMethod;
    private Paint paint = new Paint();
    private TextPaint mTextPaint;
    private TextDirectionHeuristic textDir;

    public MyCircleTextView(Context context) {
        super(context);
        init();
    }

    public MyCircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleView,
                0, 0
        );

        try {
            mLabelText = a.getString(R.styleable.CircleView_labelText);
            mLabelColor = a.getColor(R.styleable.CircleView_labelColor, 0);
            mLabelTextSize = a.getDimension(R.styleable.CircleView_labelTextSize, 1.0f);
            mLabelTextColor = a.getColor(R.styleable.CircleView_labelTextColor, 0);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }

    }

    private void init() {
        final Resources res = getResources();
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.density = res.getDisplayMetrics().density;
    }

    public int getmLabelTextColor() {
        return mLabelTextColor;
    }

    public void setmLabelTextColor(int mLabelTextColor) {
        this.mLabelTextColor = mLabelTextColor;
    }

    public float getmLabelTextSize() {
        return mLabelTextSize;
    }

    public void setmLabelTextSize(float mLabelTextSize) {
        this.mLabelTextSize = mLabelTextSize;
    }

    public int getmLabelColor() {
        return mLabelColor;
    }

    public void setmLabelColor(int mLabelColor) {
        this.mLabelColor = mLabelColor;
    }

    public String getmLabelText() {
        return mLabelText;
    }

    public void setmLabelText(String mLabelText) {
        this.mLabelText = mLabelText;
    }

    /**
     * 设置圆形的颜色
     */
    public void setCircleColor(int circleColor) {
        this.mLabelColor = circleColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = getHeight() * 0.5f;
        paint.reset();
        paint.setColor(mLabelColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(radius, radius, radius, paint);

//        paint.reset();
        mTextPaint.setTextSize(mLabelTextSize);
        mTextPaint.setColor(mLabelTextColor);

        float textWidth = mTextPaint.measureText(mLabelText);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

        float x = radius - (textWidth * 0.5f);
        float y = radius + (fontMetrics.ascent + fontMetrics.descent);
        StaticLayout staticLayout = new StaticLayout(mLabelText, mTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
//        canvas.drawText(text, x, y, mTextPaint);

        canvas.translate(0, y);
        staticLayout.draw(canvas);
    }

}
