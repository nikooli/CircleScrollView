package com.example.lsj.workspace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by lsj on 2015/12/19.
 */
public class CircleScrollView1 extends ViewGroup {
    private Paint mCirPaint;
    // ��������
    Paint paint = new Paint();
    private float mTextWidth = 0.0f;
    private float mTextHeight = 0.0f;
    /**
     * ���˺͵Ͷ����Ļ�������
     */
    private static final int MAX_SCROLL_HEIGHT = 200;
    /**
     * ���ƻ�����ʵ��
     */
    private GestureDetector mDetector;
    private Scroller mScroller;
    /**
     * ����Բ֮��ļ��
     */
    private float mCircleMargin = 0.0f;
//    private CircleView circleView;
    /**
     * RectF�洢�ĸ�float���͵�����ֵ���ֱ�Ϊ���ϡ��ҡ���
     * ͨ�����ĸ�ֵ���Գ���һ�����Ρ�
     * �������ֵ����ֱ�Ӵ�ȡ�����ҿ���ͨ��width()��height()����
     * �ܷ���Ļ�þ��εĿ�͸ߡ�ע�⣬���ﲢû���ж�ֵ�ĺϷ��ԡ�
     * ��������Ҵ��ˡ���ȷ��Ϊ��<=�ң���<=��
     */
    private RectF mCircleBounds = new RectF();
    private RectF mTriangleBounds;

    Runnable scrollerTask;
    private int initialY;
    private long newCheck = 50;

    public CircleScrollView1(Context context) {
        super(context);
        init();
    }

    /**
     * ���Ҫ��xml������ʹ�øÿؼ����������ش�attr�Ĺ��캯��
     *
     * @param context
     * @param attrs
     */
    public CircleScrollView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleScrollView1,
                0, 0
        );

        try {
            // Retrieve the values from the TypedArray and store into
            // fields of this class.
            //
            // The R.styleable.PieChart_* constants represent the index for
            // each custom attribute in the R.styleable.PieChart array.

            mTextWidth = a.getDimension(R.styleable.CircleScrollView1_labelWidth, 0.0f);
            mTextHeight = a.getDimension(R.styleable.CircleScrollView1_labelHeight, 0.0f);
            mCircleMargin = a.getDimension(R.styleable.CircleScrollView1_circleMargin, 0.0f);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }
        init();
    }

    public CircleScrollView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirPaint.setColor(Color.parseColor("#232323"));

        paint.setColor(Color.RED);// ���ú�ɫ
        paint.setStyle(Paint.Style.FILL);//��������

        mDetector = new GestureDetector(getContext(), new GestureListener());
        mDetector.setIsLongpressEnabled(false);
        if (Build.VERSION.SDK_INT < 11) {
            mScroller = new Scroller(getContext());
        } else {
            mScroller = new Scroller(getContext(), null, true);
        }
        for (int i = 0; i < 7; i++) {
            CircleView circleView = new CircleView(getContext());
            addView(circleView);
        }

        /**
         * ��ʼ���첽�߳�
         * ���ֵ�ǰѡ���item����ȷ��λ��
         */
        scrollerTask = new Runnable() {
            public void run() {
                int newY = getScrollY();
                /**
                 * �ж�initialY��newY�Ƿ���ȣ������˵���ص��˵�ǰѡ���item
                 * ���û�У���ִ��else,����ִ��scrollerTask�첽�̣߳�
                 * ֱ��initialY��newY���Ϊֹ
                 */
                if (initialY - newY == 0) { // stopped
//                    final int remainder = initialY % itemHeight;
//                    final int divided = initialY / itemHeight;
                    /**
                     * ���remainder==0����ʾitem�ص��˵�ǰѡ�е�״̬
                     * ������selectedIndexѡ�е�����Ϊdivided + offset
                     * ��ִ��ѡ�лص�
                     */
                    /*if (remainder == 0) {
                        selectedIndex = divided + offset;
                        onSeletedCallBack();
                    } else {*/
                        /**
                         * ���remainder������0�����ж�remainder�Ƿ����
                         * itemHeight��һ�룬�����򻬶�����һ�������
                         * selectedIndex = divided + offset + 1
                         * ����ص���ǰitem
                         */
                        /*if (remainder > itemHeight / 2) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    smoothScrollTo(0, initialY - remainder + itemHeight);
                                    selectedIndex = divided + offset + 1;
                                    onSeletedCallBack();
                                }
                            });
                        } else {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    CircleView.this.scrollTo(0, initialY - remainder);
                                    selectedIndex = divided + offset;
                                    onSeletedCallBack();
                                }
                            });
                        }*/
//                    }
                } else {
                    initialY = getScrollY();
                    postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTextWidth;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) mTextHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        //�����ViewGroup�Ŀ��
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = Math.min(minw, MeasureSpec.getSize(widthMeasureSpec));
        //�����ViewGroup�ĸ߶�
        int minh = getPaddingTop() + getPaddingBottom() + getSuggestedMinimumHeight();
        int h = Math.min(MeasureSpec.getSize(heightMeasureSpec), minh);

        setMeasuredDimension(w, h);
    }

    /**
     * ����������м�����View�ĳߴ��С��Ȼ�������View
     * Ҳ������onLayout�����з�����View
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());
        int count = getChildCount();
// Figure out how big we can make the pie.
//        int height = getMeasuredHeight()/2 - ;
//        float diameter = height/2;
        float tleft = getMeasuredWidth() / 2 - (getMeasuredWidth() - xpad) / 2 - getPaddingLeft();
        float ttop = getMeasuredHeight() - 20;
        float tright = 10;
        float tbottom = getMeasuredHeight() + 20;
        mTriangleBounds = new RectF(
                tleft, ttop, tright, tbottom
        );

        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            float left = getMeasuredWidth() / 2 - (getMeasuredWidth() - xpad) / 2;
            float top = getPaddingTop() + (getMeasuredWidth() - xpad) * i;
            float right = getMeasuredWidth() / 2 + (getMeasuredWidth() - xpad) / 2;
            float bottom = getPaddingTop() + (getMeasuredWidth() - xpad) * (i + 1);
            mCircleBounds = new RectF(
                    left,
                    top,
                    right,
                    bottom);
            view.layout((int) mCircleBounds.left,
                    (int) mCircleBounds.top,
                    (int) mCircleBounds.right,
                    (int) mCircleBounds.bottom);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // �������������,����Ի�����������
        Path path = new Path();
        path.moveTo(mTriangleBounds.left, mTriangleBounds.top);// �˵�Ϊ����ε����
        path.lineTo(mTriangleBounds.right, mTriangleBounds.bottom - (mTriangleBounds.bottom - mTriangleBounds.top) / 2);
        path.lineTo(mTriangleBounds.left, mTriangleBounds.bottom);
        path.close(); // ʹ��Щ�㹹�ɷ�յĶ����
        canvas.drawPath(path, paint);
    }

    private class CircleView extends View {
        RectF mBounds;

        public CircleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            float radius = (mCircleBounds.right - mCircleBounds.left) / 2;
//            float cx = mCircleBounds.right - (mCircleBounds.right - mCircleBounds.left) / 2;
//            float cy = mCircleBounds.bottom - (mCircleBounds.right - mCircleBounds.left) / 2;
            canvas.drawCircle((mBounds.right - mBounds.left) / 2, (mBounds.bottom - mBounds.top) / 2, mBounds.width() / 2, mCirPaint);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            mBounds = new RectF(0, 0, w, h);
        }
    }

    /**
     * ��ʼִ���첽�߳�
     */
    public void startScrollerTask() {
        initialY = getScrollY();
        //ִ���첽�߳�
        this.postDelayed(scrollerTask, newCheck);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the GestureDetector interpret this event
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //�ڴ���̧��ʱִ���첽�߳�
            startScrollerTask();
        }
        return mDetector.onTouchEvent(event);
    }

    /**
     * ���ƻ�������
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i("distanceX::::::", distanceX + "");
            Log.i("distanceY::::::", distanceY + "");
            Log.i("e1.getY::::::::::", e1.getY() + "");
            Log.i("e2.getY::::::::::", e2.getY() + "");
            Log.i("scrollX:::::::::", getScrollX() + "");
            Log.i("scrollY:::::::::", getScrollY() + "");

            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
            scrollBy(0, (int) (distanceY - mScroller.getCurrY()));

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Set up the Scroller for a fling

            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i("onSingleTapUp:::::::::", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            // The user is interacting with the pie, so we want to turn on acceleration
            // so that the interaction is smooth.

            return true;
        }
    }
}
