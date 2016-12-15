package com.tengio.android.graffiti;


import android.graphics.Canvas;

public abstract class FrameScalingOverlay implements Overlay {

    private double x;
    private double y;
    private double w;
    private double h;

    public FrameScalingOverlay(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    protected float convertX(Canvas canvas, double x) {
        return (float) (canvas.getWidth() * x);
    }

    protected float convertY(Canvas canvas, double y) {
        return (float) (canvas.getHeight() * y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return 1 - y;
    }

    public double getW() {
        return getX() + w;
    }

    public double getH() {
        return getY() - h;
    }

    public double getEndX() {
        return w;
    }

    public double getEndY() {
        return 1 - h;
    }
}
