package com.tengio.android.graffiti;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class FrameScalingOverlay implements Overlay {

    private double left;
    private double top;
    private double right;
    private double bottom;
    private float scaledLeft;
    private float scaledTop;
    private float scaledRight;
    private float scaledBottom;

    public FrameScalingOverlay(double left, double top, double right, double bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    protected float scaleHorizontally(Canvas canvas, double x) {
        return (float) (canvas.getWidth() * x);
    }

    protected float scaleVertically(Canvas canvas, double y) {
        return (float) (canvas.getHeight() * y);
    }

    public float getLeft() {
        return scaledLeft;
    }

    public float getTop() {
        return scaledTop;
    }

    public float getRight() {
        return scaledRight;
    }

    public float getBottom() {
        return scaledBottom;
    }

    public float getWidth() {
        return getRight() - getLeft();
    }

    public float getHeight() {
        return getBottom() - getTop();
    }

    @Override
    public void draw(Canvas canvas, Paint paint, boolean debugMode) {
        paint.setColor(Color.BLACK);
        this.scaledTop = scaleVertically(canvas, top);
        this.scaledLeft = scaleHorizontally(canvas, left);
        this.scaledRight = scaleHorizontally(canvas, right);
        this.scaledBottom = scaleVertically(canvas, bottom);
        if (debugMode) {
            debugRect(canvas);
        }
    }

    private void debugRect(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(2);
        canvas.drawLine(getLeft() + 1, 0, getLeft() + 1, canvas.getHeight(), p);
        canvas.drawLine(0, getTop() + 1, canvas.getWidth(), getTop() + 1, p);
        p.setColor(Color.BLUE);
        canvas.drawLine(getRight() - 1, 0, getRight() - 1, canvas.getHeight(), p);
        canvas.drawLine(0, getBottom() - 1, canvas.getWidth(), getBottom() - 1, p);
        //canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), p);
    }
}
