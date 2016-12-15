package com.tengio.android.graffiti;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LineOverlayPaint extends FrameScalingOverlay {

    public LineOverlayPaint(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        canvas.drawLine(convertX(canvas, getX()),
                convertY(canvas, getY()),
                convertX(canvas, getEndX()),
                convertY(canvas, getEndY()),
                paint);
    }
}
