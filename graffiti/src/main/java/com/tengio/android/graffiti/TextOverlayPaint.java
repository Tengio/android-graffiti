package com.tengio.android.graffiti;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TextOverlayPaint extends FrameScalingOverlay {

    private static final int CENTER_ALIGN = 1;
    private static final int LEFT_ALIGN = 0;
    private static final int RIGHT_ALIGN = 2;

    private int alignment;
    private double fontScale;

    private float textSize;
    private Canvas canvas;
    private Paint paint;
    private String text;

    public TextOverlayPaint(String text, double fontScale, int alignment, double x, double y, double w, double h) {
        super(x, y, w, h);
        this.fontScale = fontScale;
        this.alignment = alignment;
        this.text = text;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, boolean debugMode) {
        super.draw(canvas, paint, debugMode);
        this.paint = paint;
        this.canvas = canvas;
        this.textSize = (float) (getHeight() * fontScale * 0.85);
        paint.setTextSize(textSize);
        float m = paint.measureText(text);
        if (getWidth() > m) {
            drawText(text, getTop() + textSize);
            return;
        }
        recursiveDrawing("", text, getTop() + textSize);
        this.paint = null;
        this.canvas = null;
    }

    private void recursiveDrawing(String drawing, String remaining, float y) {
        String[] parts = remaining.split(" ", 2);
        if (parts.length == 1) {
            if (canDraw(drawing + " " + parts[0])) {
                if (drawing.length() == 0) {
                    drawText(parts[0], y);
                } else {
                    drawText(drawing + " " + parts[0], y);
                }
            } else {
                if (drawing.length() > 0) {
                    drawText(drawing, y);
                    drawText(parts[0], y + textSize);
                } else {
                    drawText(parts[0], y);
                }
            }
        } else {
            if (canDraw(drawing + " " + parts[0])) {
                if (drawing.length() == 0) {
                    recursiveDrawing(parts[0], parts[1], y);
                } else {
                    recursiveDrawing(drawing + " " + parts[0], parts[1], y);
                }
            } else {
                if (drawing.length() > 0) {
                    drawText(drawing, y);
                    recursiveDrawing(parts[0], parts[1], y + textSize);
                } else {
                    recursiveDrawing(parts[0], parts[1], y);
                }
            }
        }
    }

    private void drawText(String drawing, float y) {
        if (isCenterAlign()) {
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(drawing, getCenter(), y, paint);
        } else if (isRightAlign()) {
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(drawing, getLeft(), y, paint);
        } else {
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(drawing, getLeft(), y, paint);
        }
    }

    private boolean canDraw(String text) {
        return getWidth() > paint.measureText(text);
    }

    private float getCenter() {
        return getLeft() + (getWidth() / 2);
    }

    public boolean isCenterAlign() {
        return alignment == CENTER_ALIGN;
    }

    public boolean isRightAlign() {
        return alignment == RIGHT_ALIGN;
    }
}
