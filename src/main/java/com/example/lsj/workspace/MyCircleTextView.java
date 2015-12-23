package com.example.lsj.workspace;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
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
public class MyCircleTextView extends View implements ValueAnimator.AnimatorUpdateListener{
    /**
     * 圆形的半径
     */
    private float mLabelRadius;
    private int mLabelTextColor;
    private float mLabelTextSize;
    private int mLabelColor;

    private String mLabelText;
    private RectF mBounds;
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
            mLabelRadius = a.getDimension(R.styleable.CircleView_labelRadius, 48.0f);
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

    public float getmLabelRadius() {
        return mLabelRadius;
    }

    public void setmLabelRadius(int mLabelRadius) {
        this.mLabelRadius = mLabelRadius;
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = View.MeasureSpec.makeMeasureSpec(widthMeasureSpec, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(heightMeasureSpec, View.MeasureSpec.UNSPECIFIED);
        setMeasuredDimension(width, height);
    }

    /*@Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }*/

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float l = (w - mLabelRadius * 2) / 2;
        float t = (h - mLabelRadius * 2) / 2;
        float r = l + mLabelRadius * 2;
        float b = t + mLabelRadius * 2;
        mBounds = new RectF(l, t, r, b);
        layout((int) mBounds.left, (int) mBounds.top, (int) mBounds.right, (int) mBounds.bottom);
    }

    /**
     * 获取圆形区域的中心点x坐标
     *
     * @return
     */
    public float getCenterX() {
        return mLabelRadius;
    }

    public float getCenterY() {
        return mLabelRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = mLabelRadius;
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
        StaticLayout staticLayout = new StaticLayout(
                mLabelText, mTextPaint, (int) mLabelRadius * 2, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
//        canvas.drawText(text, x, y, mTextPaint);

        canvas.translate(0, y);
        staticLayout.draw(canvas);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }
}
