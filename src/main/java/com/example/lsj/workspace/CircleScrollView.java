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
     * ƫ��������Ҫ����ǰ�������油ȫ��
     * ��˼���м�ѡ���е����¸��м�����ʾ�����ƫ�������ó�n�������¸���n��
     * ��ʾ��ѡ���ݡ�Ϊ�˱�֤�������һ�л��������һ�������ܻ�����ѡ����ڣ�
     * ���뽫listǰ������Ͽ��ַ�����Ӵ���ȡ����ƫ����offset��ֵ
     * ƫ�������ֵΪ4����Ϊ�м�ѡ����Ĵ�СΪ100%��Ȼ������Ϊ80%��60%��40%��20%
     */
    int offset = OFF_SET_DEFAULT;
    /**
     * Ĭ�ϵ�ƫ����
     */
    public static final int OFF_SET_DEFAULT = 2;
    /**
     * ÿҳ��ʾ����������Ҫ����ƫ����offset������
     * offset * 2 + 1
     */
    int displayItemCount;
    /**
     * item�ĸ߶�
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
        trianglePaint.setColor(Color.RED);// ���ú�ɫ
        trianglePaint.setStyle(Paint.Style.FILL);//��������

        root_view = new LinearLayout(context);
        root_view.setOrientation(LinearLayout.VERTICAL);
        this.addView(root_view);

    }

    /**
     * �������
     */
    public void setItems() {

        initData();
    }

    /**
     * ��ʼ������,��list������ӽ���ͼ��
     */
    private void initData() {
        //������ʾ������
        displayItemCount = offset * 2 + 1;
        for (int i = 0; i < 7; i++) {
            root_view.addView(createView(i, 100));
        }
    }
    /*@Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Path path = new Path();
        path.moveTo(mTriangleBounds.left, mTriangleBounds.top);// �˵�Ϊ����ε����
        path.lineTo(mTriangleBounds.right, mTriangleBounds.bottom - (mTriangleBounds.bottom - mTriangleBounds.top) / 2);
        path.lineTo(mTriangleBounds.left, mTriangleBounds.bottom);
        path.close(); // ʹ��Щ�㹹�ɷ�յĶ����
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
            path.moveTo(mTriangleBounds.left, mTriangleBounds.top);// �˵�Ϊ����ε����
            path.lineTo(mTriangleBounds.right, mTriangleBounds.bottom - (mTriangleBounds.bottom - mTriangleBounds.top) / 2);
            path.lineTo(mTriangleBounds.left, mTriangleBounds.bottom);
            path.close(); // ʹ��Щ�㹹�ɷ�յĶ����
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
