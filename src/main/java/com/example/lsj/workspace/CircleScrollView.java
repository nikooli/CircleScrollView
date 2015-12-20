package com.example.lsj.workspace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lsj on 2015/12/19.
 */
public class CircleScrollView extends ViewGroup {
    private Paint mCirPaint;
    private float mTextWidth = 0.0f;
    private CircleView circleView;
    /**
     * RectF存储四个float类型的坐标值，分别为左、上、右、下
     * 通过这四个值可以呈现一个矩形。
     * 这里面的值可以直接存取，并且可以通过width()和height()函数
     * 很方便的获得矩形的宽和高。注意，这里并没有判断值的合法性。
     * 比如左比右大了。正确的为左<=右，上<=下
     */
    private RectF mCircleBounds = new RectF();

    public CircleScrollView(Context context) {
        super(context);
        init();
    }

    /**
     * 如果要在xml布局中使用该控件，必须重载带attr的构造函数
     *
     * @param context
     * @param attrs
     */
    public CircleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleScrollView,
                0, 0
        );

        try {
            // Retrieve the values from the TypedArray and store into
            // fields of this class.
            //
            // The R.styleable.PieChart_* constants represent the index for
            // each custom attribute in the R.styleable.PieChart array.

            mTextWidth = a.getDimension(R.styleable.CircleScrollView_labelWidth, 0.0f);

        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }
        init();
    }

    public CircleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirPaint.setColor(Color.parseColor("#232323"));

        circleView = new CircleView(getContext());
        addView(circleView);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTextWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        //计算该ViewGroup的宽度
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = Math.min(minw, MeasureSpec.getSize(widthMeasureSpec));
        //计算该ViewGroup的高度
        int minh = (w - (int) mTextWidth) + getPaddingBottom() + getPaddingTop();
        int h = Math.max(MeasureSpec.getSize(heightMeasureSpec), minh);

        setMeasuredDimension(w, h);
    }

    /**
     * 在这个函数中计算子View的尺寸大小，然后放置子View
     * 也可以在onLayout函数中放置子View
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

// Figure out how big we can make the pie.
//        float diameter = Math.min(ww, hh);
        mCircleBounds = new RectF(
                0.0f,
                0.0f,
                100.0f,
                100.0f);
        circleView.layout((int) mCircleBounds.left,
                (int) mCircleBounds.top,
                (int) mCircleBounds.right,
                (int) mCircleBounds.bottom);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private class CircleView extends View {

        public CircleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(100, 100, 100, mCirPaint);
        }
    }
}
