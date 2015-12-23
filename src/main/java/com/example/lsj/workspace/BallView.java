package com.example.lsj.workspace;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;

import com.example.lsj.workspace.utils.BallShapeHolder;

/**
 * Created by lsj on 2015/12/23.
 */
public class BallView extends View implements ValueAnimator.AnimatorUpdateListener {
    private Animator bounceAnim;
    private static final float BALL_SIZE = 200f;
    private BallShapeHolder shapeHolder;

    private static final int DURATION = 1500;

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
        shapeHolder.setText("ÄãºÃ");
        Paint paint = drawable.getPaint();
        paint.setColor(Color.parseColor("#00dfc1"));
        shapeHolder.setPaint(paint);

        TextPaint mTextPaint = new TextPaint();
        mTextPaint.setTextSize(20.0f);
        mTextPaint.setColor(Color.parseColor("#000000"));
        float textWidth = mTextPaint.measureText("ÄãºÃ");
        StaticLayout staticLayout = new StaticLayout(
                "ÄãºÃ", mTextPaint, (int) 100 * 2, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);

        shapeHolder.setStaticLayout(staticLayout);
    }

    private void createAnimation() {
        if (bounceAnim == null) {
            PropertyValuesHolder pvhW = PropertyValuesHolder.ofFloat("width", shapeHolder.getWidth(),
                    shapeHolder.getWidth() * 2);
            PropertyValuesHolder pvhH = PropertyValuesHolder.ofFloat("height", shapeHolder.getHeight(),
                    shapeHolder.getHeight() * 2);
            PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat("x", shapeHolder.getX(),
                    shapeHolder.getX() - BALL_SIZE / 2f);
            PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat("y", shapeHolder.getY(),
                    shapeHolder.getY() - BALL_SIZE / 2f);
            /*ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(pvhW, pvhH, pvTX,pvTY).setDuration(DURATION/2);
            valueAnimator.setRepeatCount(1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.start();*/
            ObjectAnimator whxyBouncer = ObjectAnimator.ofPropertyValuesHolder(shapeHolder, pvhW, pvhH,
                    pvTX, pvTY).setDuration(DURATION / 2);
            whxyBouncer.setRepeatCount(1);
            whxyBouncer.setRepeatMode(ValueAnimator.REVERSE);
            whxyBouncer.addUpdateListener(this);
            whxyBouncer.start();
//            bounceAnim = new AnimatorSet();
//            ((AnimatorSet)bounceAnim).play(valueAnimator);
        }
    }

    public void startAnimation() {
        createAnimation();
//        bounceAnim.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(shapeHolder.getX(), shapeHolder.getY());
        shapeHolder.getShape().draw(canvas);
        canvas.translate(-shapeHolder.getX(), -shapeHolder.getY());

//        float x = radius - (textWidth * 0.5f);

        Paint.FontMetrics fontMetrics = shapeHolder.getStaticLayout().getPaint().getFontMetrics();
        float y = 100 + (fontMetrics.ascent + fontMetrics.descent);

        canvas.translate(0, y);
        shapeHolder.getStaticLayout().draw(canvas);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }
}
