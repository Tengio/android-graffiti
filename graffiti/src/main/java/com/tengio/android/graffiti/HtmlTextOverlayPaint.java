package com.tengio.android.graffiti;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class HtmlTextOverlayPaint extends FrameScalingOverlay {

    private Layout.Alignment alignment;
    private double fontScale;
    private String text;

    public HtmlTextOverlayPaint(String text, double fontScale, int alignment, double x, double y, double w, double h) {
        super(x, y, w, h);
        this.fontScale = fontScale;
        this.text = text;
        if (alignment == 0) {
            this.alignment = Layout.Alignment.ALIGN_NORMAL;
        } else if (alignment == 2) {
            this.alignment = Layout.Alignment.ALIGN_OPPOSITE;
        } else {
            this.alignment = Layout.Alignment.ALIGN_CENTER;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint, boolean debugMode) {
        super.draw(canvas, paint, debugMode);
        TextPaint tp = new TextPaint();
        tp.setAntiAlias(true);
        tp.setTypeface(paint.getTypeface());
        tp.setTextSize((float) (getHeight() * fontScale * 0.85));
        tp.setColor(Color.BLACK);
        StaticLayout tl = new StaticLayout(Html.fromHtml(text), tp, (int) getWidth(),
                alignment, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(getLeft(), getTop());
        tl.draw(canvas);
        canvas.restore();

    }
}
