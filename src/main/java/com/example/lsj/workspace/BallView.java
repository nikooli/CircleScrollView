package com.example.lsj.workspace;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

import com.example.lsj.workspace.utils.BallShapeHolder;

/**
 * Created by lsj on 2015/12/23.
 */
public class BallView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final float BALL_SIZE = 100f;
    private BallShapeHolder shapeHolder;

    public BallView(Context context) {
        super(context);
        init(80, 0);
    }

    public void init(int x, int y) {
        OvalShape circle = new OvalShape();
        circle.resize(BALL_SIZE, BALL_SIZE);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        shapeHolder = new BallShapeHolder(drawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        Paint paint = drawable.getPaint();
        paint.setColor(Color.parseColor("#00dfc1"));
        shapeHolder.setPaint(paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(shapeHolder.getX(), shapeHolder.getY());
        shapeHolder.getShape().draw(canvas);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }
}
