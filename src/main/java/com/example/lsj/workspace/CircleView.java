package com.example.lsj.workspace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lsj on 2015/12/19.
 */
public class CircleView extends View {
    private Paint mCirPaint;

    public CircleView(Context context) {
        super(context);
        init();

    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirPaint.setColor(Color.parseColor("#232323"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(100, 100, 100, mCirPaint);
    }
}
