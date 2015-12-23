package com.example.lsj.workspace.utils;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

/**
 * Created by lsj on 2015/12/23.
 * 定义球的属性信息
 */
public class BallShapeHolder {

    private float x = 0;
    private float y = 0;
    private int color;
    private Paint paint;
    private ShapeDrawable shape;

    public BallShapeHolder(ShapeDrawable shape) {
        this.shape = shape;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public ShapeDrawable getShape() {
        return shape;
    }

    public void setShape(ShapeDrawable shape) {
        this.shape = shape;
    }
}
