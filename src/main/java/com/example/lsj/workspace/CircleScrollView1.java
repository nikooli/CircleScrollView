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
    // 创建画笔
    Paint paint = new Paint();
    private float mTextWidth = 0.0f;
    private float mTextHeight = 0.0f;
    /**
     * 顶端和低端最大的滑动距离
     */
    private static final int MAX_SCROLL_HEIGHT = 200;
    /**
     * 手势滑动的实现
     */
    private GestureDetector mDetector;
    private Scroller mScroller;
    /**
     * 两个圆之间的间距
     */
    private float mCircleMargin = 0.0f;
//    private CircleView circleView;
    /**
     * RectF存储四个float类型的坐标值，分别为左、上、右、下
     * 通过这四个值可以呈现一个矩形。
     * 这里面的值可以直接存取，并且可以通过width()和height()函数
     * 很方便的获得矩形的宽和高。注意，这里并没有判断值的合法性。
     * 比如左比右大了。正确的为左<=右，上<=下
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
     * 如果要在xml布局中使用该控件，必须重载带attr的构造函数
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

        paint.setColor(Color.RED);// 设置红色
        paint.setStyle(Paint.Style.FILL);//设置填满

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
         * 初始化异步线程
         * 保持当前选择的item在正确的位置
         */
        scrollerTask = new Runnable() {
            public void run() {
                int newY = getScrollY();
                /**
                 * 判断initialY和newY是否相等，相等则说明回到了当前选择的item
                 * 如果没有，则执行else,继续执行scrollerTask异步线程，
                 * 直到initialY和newY相等为止
                 */
                if (initialY - newY == 0) { // stopped
//                    final int remainder = initialY % itemHeight;
//                    final int divided = initialY / itemHeight;
                    /**
                     * 如果remainder==0，表示item回到了当前选中的状态
                     * 则设置selectedIndex选中的索引为divided + offset
                     * 并执行选中回调
                     */
                    /*if (remainder == 0) {
                        selectedIndex = divided + offset;
                        onSeletedCallBack();
                    } else {*/
                        /**
                         * 如果remainder不等于0，则判断remainder是否大于
                         * itemHeight的一半，大于则滑动到下一项，即设置
                         * selectedIndex = divided + offset + 1
                         * 否则回到当前item
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
        //计算该ViewGroup的宽度
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = Math.min(minw, MeasureSpec.getSize(widthMeasureSpec));
        //计算该ViewGroup的高度
        int minh = getPaddingTop() + getPaddingBottom() + getSuggestedMinimumHeight();
        int h = Math.min(MeasureSpec.getSize(heightMeasureSpec), minh);

        setMeasuredDimension(w, h);
    }

    /**
     * 在这个函数中计算子View的尺寸大小，然后放置子View
     * 也可以在onLayout函数中放置子View
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
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(mTriangleBounds.left, mTriangleBounds.top);// 此点为多边形的起点
        path.lineTo(mTriangleBounds.right, mTriangleBounds.bottom - (mTriangleBounds.bottom - mTriangleBounds.top) / 2);
        path.lineTo(mTriangleBounds.left, mTriangleBounds.bottom);
        path.close(); // 使这些点构成封闭的多边形
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
     * 开始执行异步线程
     */
    public void startScrollerTask() {
        initialY = getScrollY();
        //执行异步线程
        this.postDelayed(scrollerTask, newCheck);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the GestureDetector interpret this event
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //在触摸抬起时执行异步线程
            startScrollerTask();
        }
        return mDetector.onTouchEvent(event);
    }

    /**
     * 手势滑动监听
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
