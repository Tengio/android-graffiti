package com.tengio.android.graffiti;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LineOverlayPaint extends FrameScalingOverlay {

    public LineOverlayPaint(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, boolean debugMode) {
        super.draw(canvas, paint, debugMode);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); //TODO
        canvas.drawLine(getLeft(), getTop(), getRight(), getBottom(), paint);
    }
}
