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
     * RectF�洢�ĸ�float���͵�����ֵ���ֱ�Ϊ���ϡ��ҡ���
     * ͨ�����ĸ�ֵ���Գ���һ�����Ρ�
     * �������ֵ����ֱ�Ӵ�ȡ�����ҿ���ͨ��width()��height()����
     * �ܷ���Ļ�þ��εĿ�͸ߡ�ע�⣬���ﲢû���ж�ֵ�ĺϷ��ԡ�
     * ��������Ҵ��ˡ���ȷ��Ϊ��<=�ң���<=��
     */
    private RectF mCircleBounds = new RectF();

    public CircleScrollView(Context context) {
        super(context);
        init();
    }

    /**
     * ���Ҫ��xml������ʹ�øÿؼ����������ش�attr�Ĺ��캯��
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
        //�����ViewGroup�Ŀ��
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = Math.min(minw, MeasureSpec.getSize(widthMeasureSpec));
        //�����ViewGroup�ĸ߶�
        int minh = (w - (int) mTextWidth) + getPaddingBottom() + getPaddingTop();
        int h = Math.max(MeasureSpec.getSize(heightMeasureSpec), minh);

        setMeasuredDimension(w, h);
    }

    /**
     * ����������м�����View�ĳߴ��С��Ȼ�������View
     * Ҳ������onLayout�����з�����View
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
