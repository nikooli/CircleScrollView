package com.example.lsj.workspace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.lsj.workspace.utils.ABViewUtil;

/**
 * Created by lsj on 2015/12/21.
 */
public class CircleScrollView extends ScrollView {

    private float mTextWidth = 0.0f;
    private float mTextHeight = 0.0f;
    private Context context;
    /**
     * 偏移量（需要在最前面和最后面补全）
     * 意思是中间选中行的上下各有几行显示，如果偏移量设置成n，则上下各有n行
     * 显示待选数据。为了保证最上面的一行或最下面的一行数据能滑动到选择框内，
     * 必须将list前后添加上空字符，添加次数取决于偏移量offset的值
     * 偏移量最大值为4，因为中间选中项的大小为100%，然后依次为80%、60%、40%、20%
     */
    int offset = OFF_SET_DEFAULT;
    /**
     * 默认的偏移量
     */
    public static final int OFF_SET_DEFAULT = 2;
    /**
     * 每页显示的数量，需要根据偏移量offset来计算
     * offset * 2 + 1
     */
    int displayItemCount;
    /**
     * item的高度
     */
    int itemHeight = 0;
    int viewWidth;
    private int initialY;
    private long newCheck = 50;
    Runnable scrollerTask;
    private LinearLayout root_view;
    private LayoutInflater layoutInflater;
    private Paint trianglePaint;
    private RectF mTriangleBounds;

    public CircleScrollView(Context context) {
        super(context);
    }

    public CircleScrollView(Context context, AttributeSet attrs) {
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
            offset = a.getInteger(R.styleable.CircleScrollView1_labelOffset, 2);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }
        init(context);
    }

    public CircleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.setVerticalScrollBarEnabled(false);

        trianglePaint = new Paint();
        trianglePaint.setColor(Color.RED);// 设置红色
        trianglePaint.setStyle(Paint.Style.FILL);//设置填满

        root_view = new LinearLayout(context);
        root_view.setOrientation(LinearLayout.VERTICAL);
        this.addView(root_view);

    }

    /**
     * 填充数据
     */
    public void setItems() {

        initData();
    }

    /**
     * 初始化数据,将list数据添加进视图中
     */
    private void initData() {
        //计算显示的数量
        displayItemCount = offset * 2 + 1;
        for (int i = 0; i < 7; i++) {
            root_view.addView(createView(i, 100));
        }
    }
    /*@Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Path path = new Path();
        path.moveTo(mTriangleBounds.left, mTriangleBounds.top);// 此点为多边形的起点
        path.lineTo(mTriangleBounds.right, mTriangleBounds.bottom - (mTriangleBounds.bottom - mTriangleBounds.top) / 2);
        path.lineTo(mTriangleBounds.left, mTriangleBounds.bottom);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, trianglePaint);
    }*/

    private View createView(int position, int per) {
        MyCircleTextView circleTextView = new MyCircleTextView(context);
        if (position == (offset + 1) || position == (offset - 1)) {
            circleTextView.setmLabelRadius(100 * 80 / 100);
        }
        if (position == (offset + 2) || position == (offset - 2)) {
            circleTextView.setmLabelRadius(100 * 60 / 100);
        }
        if (position == offset) {
            circleTextView.setmLabelRadius(100 * 100 / 100);
        }
        circleTextView.setmLabelText("dddddd");
        circleTextView.setmLabelColor(Color.parseColor("#00dfc1"));
        circleTextView.setmLabelTextSize(20.0f);
        circleTextView.setmLabelTextColor(context.getResources().getColor(R.color.black));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                200, 200);
        params.gravity = Gravity.CENTER;
        circleTextView.setLayoutParams(params);

        itemHeight = ABViewUtil.getViewMeasuredHeight(circleTextView);
        ViewGroup.LayoutParams rlp = root_view.getLayoutParams();
        rlp.height = 200 * displayItemCount;
        root_view.setLayoutParams(rlp);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
        lp.height = 200 * displayItemCount;
        this.setLayoutParams(lp);
        return circleTextView;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        /*if (mTriangleBounds != null) {
            Canvas canvas = new Canvas();
            Path path = new Path();
            path.moveTo(mTriangleBounds.left, mTriangleBounds.top);// 此点为多边形的起点
            path.lineTo(mTriangleBounds.right, mTriangleBounds.bottom - (mTriangleBounds.bottom - mTriangleBounds.top) / 2);
            path.lineTo(mTriangleBounds.left, mTriangleBounds.bottom);
            path.close(); // 使这些点构成封闭的多边形
            canvas.drawPath(path, trianglePaint);
            background.draw(canvas);
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleBounds = new RectF(
                0.0f, 0.0f, 20.0f, 20.0f
        );
        viewWidth = w;
        setBackground(null);
    }
}
