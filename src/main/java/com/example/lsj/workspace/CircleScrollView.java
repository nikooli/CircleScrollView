package com.example.lsj.workspace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lsj on 2015/12/19.
 */
public class CircleScrollView extends ViewGroup {
    private Paint mCirPaint;
    public CircleScrollView(Context context) {
        super(context);
        init();
    }

    /**
     * 如果要在xml布局中使用该控件，必须重载带attr的构造函数
     * @param context
     * @param attrs
     */
    public CircleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirPaint.setColor(Color.parseColor("#232323"));

        CircleView circleView = new CircleView(getContext());
        addView(circleView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        // Try for a width based on our minimum
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // 设置自定义的控件MyViewGroup的大小
        setMeasuredDimension(measureWidth, measureHeight);
    }
    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸

        switch (widthMode) {
            /**
             * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
             * MeasureSpec.AT_MOST。
             *
             *
             * MeasureSpec.EXACTLY是精确尺寸，
             * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
             * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
             *
             *
             * MeasureSpec.AT_MOST是最大尺寸，
             * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
             * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
             * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
             *
             *
             * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
             * 通过measure方法传入的模式。
             */
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;

        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();

        // These are the far left and right edges in which we are performing layout.
        int leftPos = getPaddingLeft();
        int rightPos = getPaddingRight();
//        int rightPos = r - l - getPaddingRight();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                // Compute the frame in which we are placing this child.
                /*if (lp.position == LayoutParams.POSITION_LEFT) {
                    mTmpContainerRect.left = leftPos + lp.leftMargin;
                    mTmpContainerRect.right = leftPos + width + lp.rightMargin;
                    leftPos = mTmpContainerRect.right;
                } else if (lp.position == LayoutParams.POSITION_RIGHT) {
                    mTmpContainerRect.right = rightPos - lp.rightMargin;
                    mTmpContainerRect.left = rightPos - width - lp.leftMargin;
                    rightPos = mTmpContainerRect.left;
                } else {
                    mTmpContainerRect.left = middleLeft + lp.leftMargin;
                    mTmpContainerRect.right = middleRight - lp.rightMargin;
                }
                mTmpContainerRect.top = parentTop + lp.topMargin;
                mTmpContainerRect.bottom = parentBottom - lp.bottomMargin;*/

                // Use the child's gravity and size to determine its final
                // frame within its container.
//                Gravity.apply(lp.gravity, width, height, mTmpContainerRect, mTmpChildRect);

                // Place the child.
//                child.layout(mTmpChildRect.left, mTmpChildRect.top,
//                        mTmpChildRect.right, mTmpChildRect.bottom);
                child.layout(l,width,0,height);
            }
        }
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
