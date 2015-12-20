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
     * ���Ҫ��xml������ʹ�øÿؼ����������ش�attr�Ĺ��캯��
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
        // �����Զ����ViewGroup�������ӿؼ��Ĵ�С
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // �����Զ���Ŀؼ�MyViewGroup�Ĵ�С
        setMeasuredDimension(measureWidth, measureHeight);
    }
    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// �õ�ģʽ
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// �õ��ߴ�

        switch (widthMode) {
            /**
             * mode�������������ȡֵ�ֱ�ΪMeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
             * MeasureSpec.AT_MOST��
             *
             *
             * MeasureSpec.EXACTLY�Ǿ�ȷ�ߴ磬
             * �����ǽ��ؼ���layout_width��layout_heightָ��Ϊ������ֵʱ��andorid
             * :layout_width="50dip"������ΪFILL_PARENT�ǣ����ǿؼ���С�Ѿ�ȷ������������Ǿ�ȷ�ߴ硣
             *
             *
             * MeasureSpec.AT_MOST�����ߴ磬
             * ���ؼ���layout_width��layout_heightָ��ΪWRAP_CONTENTʱ
             * ���ؼ���Сһ�����ſؼ����ӿռ�����ݽ��б仯����ʱ�ؼ��ߴ�ֻҪ���������ؼ���������ߴ缴��
             * ����ˣ���ʱ��mode��AT_MOST��size�����˸��ؼ���������ߴ硣
             *
             *
             * MeasureSpec.UNSPECIFIED��δָ���ߴ磬����������࣬һ�㶼�Ǹ��ؼ���AdapterView��
             * ͨ��measure���������ģʽ��
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
