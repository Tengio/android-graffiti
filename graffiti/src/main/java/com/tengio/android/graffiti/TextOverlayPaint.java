package com.tengio.android.graffiti;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class TextOverlayPaint extends FrameScalingOverlay {

    private static final int CENTER_ALIGN = 1;
    private static final int LEFT_ALIGN = 0;
    private static final int RIGHT_ALIGN = 2;

    private int alignment;
    private String content;
    private double fontScale;

    private float textSize;
    private double availableWidth;
    private Canvas canvas;
    private Paint paint;
    private String text;

    public TextOverlayPaint(String text, double fontScale, int alignment, double x, double y, double w, double h) {
        super(x, y, w, h);
        this.fontScale = fontScale;
        this.alignment = alignment;
        this.content = text;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        this.paint = paint;
        this.canvas = canvas;
        this.text = getText();
        this.textSize = getTextSize();

        paint.setTextSize(textSize);
        if (isCenterAlign()) {
            paint.setTextAlign(Paint.Align.CENTER);
            drawText(getCenter(), convertY(getH()) + textSize);
        } else if (isRightAlign()) {
            paint.setTextAlign(Paint.Align.RIGHT);
            drawText(convertX(getW()), convertY(getH()) + textSize);
        } else {
            paint.setTextAlign(Paint.Align.LEFT);
            drawText(convertX(getX()), convertY(getH()) + textSize);
        }
        //TODO
        debugRect();
        this.paint = null;
        this.canvas = null;
        this.text = null;
    }

    private float getTextSize() {
        return (float) ((convertY(getY() - getH())) * getFontScale() * 0.85);
    }

    private void drawText(float x, float y) {
        float m = paint.measureText(text);
        availableWidth = (getW() - getX()) * canvas.getWidth();
        if (availableWidth > m) {
            drawText(text, x, y);
            return;
        }
        recursiveDrawing("", text, x, y);

    }

    private void recursiveDrawing(String drawing, String remaining, float x, float y) {
        String[] parts = remaining.split(" ", 2);
        if (parts.length == 1) {
            if (canDraw(drawing + " " + parts[0])) {
                if (drawing.length() == 0) {
                    drawText(parts[0], x, y);
                } else {
                    drawText(drawing + " " + parts[0], x, y);
                }
            } else {
                if (drawing.length() > 0) {
                    drawText(drawing, x, y);
                    drawText(parts[0], x, y + textSize);
                } else {
                    drawText(parts[0], x, y);
                }
            }
        } else {
            if (canDraw(drawing + " " + parts[0])) {
                if (drawing.length() == 0) {
                    recursiveDrawing(parts[0], parts[1], x, y);
                } else {
                    recursiveDrawing(drawing + " " + parts[0], parts[1], x, y);
                }
            } else {
                if (drawing.length() > 0) {
                    drawText(drawing, x, y);
                    recursiveDrawing(parts[0], parts[1], x, y + textSize);
                } else {
                    recursiveDrawing(parts[0], parts[1], x, y);
                }
            }
        }
    }

    private void drawText(String drawing, float x, float y) {
        if (hasBoldText() && drawing.contains("ad.")) { //TODO this hardcoded version probably will not work
            String[] parts = text.split(" ");
            //TODO get font from somewhere
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            float m = paint.measureText(parts[1]);
            canvas.drawText(parts[0] + " ", x - m, y, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvas.drawText(parts[1], x, y, paint);
        } else {
            canvas.drawText(drawing, x, y, paint);
        }
    }

    private boolean canDraw(String text) {
        return availableWidth > paint.measureText(text);
    }

    private float getCenter() {
        return convertX(canvas, getX() + (getW() - getX()) / 2);
    }

    private float convertX(double x) {
        return super.convertX(canvas, x);
    }

    private float convertY(double y) {
        return super.convertY(canvas, y);
    }

    private String getText() {
        return getContent().replace("<b>", "").replace("</b>", "");
    }

    private boolean hasBoldText() {
        return getContent().contains("<b>");
    }

    private void debugRect() {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(2);
        canvas.drawRect(convertX(canvas, getX()), convertY(canvas, getY()),
                convertX(canvas, getW()), convertY(canvas, getH()), p);
    }

    public double getFontScale() {
        return fontScale;
    }

    public String getContent() {
        return content;
    }

    public int getAlignment() {
        return alignment;
    }

    public boolean isCenterAlign() {
        return alignment == CENTER_ALIGN;
    }

    public boolean isRightAlign() {
        return alignment == RIGHT_ALIGN;
    }
}
