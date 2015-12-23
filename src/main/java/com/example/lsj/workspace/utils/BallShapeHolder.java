package com.example.lsj.workspace.utils;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.text.StaticLayout;

/**
 * Created by lsj on 2015/12/23.
 * �������������Ϣ
 */
public class BallShapeHolder {
private String text;
    private float x = 0;
    private float y = 0;
    private int color;
    private Paint paint;
    private StaticLayout staticLayout;
    private ShapeDrawable shape;

    public BallShapeHolder(ShapeDrawable shape) {
        this.shape = shape;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getX() {
        return x;
    }

    public void setX(float value) {
        x = value;
    }

    public float getY() {
        return y;
    }

    public void setY(float value) {
        y = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public StaticLayout getStaticLayout() {
        return staticLayout;
    }

    public void setStaticLayout(StaticLayout staticLayout) {
        this.staticLayout = staticLayout;
    }
    public float getWidth() {
        return shape.getShape().getWidth();
    }
    public void setWidth(float width) {
        Shape s = shape.getShape();
        s.resize(width, s.getHeight());
    }

    public float getHeight() {
        return shape.getShape().getHeight();
    }
    public void setHeight(float height) {
        Shape s = shape.getShape();
        s.resize(s.getWidth(), height);
    }
    public ShapeDrawable getShape() {
        return shape;
    }

    public void setShape(ShapeDrawable shape) {
        this.shape = shape;
    }
}
