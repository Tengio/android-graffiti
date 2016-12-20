package com.tengio.android.graffiti;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;

import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

public class PostprocessorPainter extends BasePostprocessor {

    private List<? extends Overlay> overlays;
    private boolean debug;
    private double left;
    private double right;
    private double top;
    private double bottom;
    private Typeface typeface = Typeface.DEFAULT;

    @Override
    public String getName() {
        return PostprocessorPainter.class.getSimpleName();
    }

    private void setFrame(double left, double top, double right, double bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        double widthIncrease = left + right;
        double heightIncrease = top + bottom;
        int xOffset = (int) (sourceBitmap.getWidth() * left);
        int yOffset = (int) (sourceBitmap.getHeight() * top);
        int w = (int) (sourceBitmap.getWidth() * widthIncrease);
        int h = (int) (sourceBitmap.getHeight() * heightIncrease);
        CloseableReference<Bitmap> bitmapRef = bitmapFactory.createBitmap(w, h, Bitmap.Config.RGB_565);
        try {
            Bitmap destBitmap = bitmapRef.get();
            Canvas canvas = new Canvas(destBitmap);
            if (debug) {
                canvas.drawColor(0xFFAB3931);
            } else {
                canvas.drawColor(0xffffffff);
            }
            canvas.drawBitmap(sourceBitmap, xOffset, yOffset, null);
            process(canvas);
            return CloseableReference.cloneOrNull(bitmapRef);
        } finally {
            CloseableReference.closeSafely(bitmapRef);
        }
    }

    protected void process(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        if (overlays == null) {
            return;
        }
        for (Overlay overlay : overlays) {
            try {
                overlay.draw(canvas, paint, debug);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class Builder {
        private PostprocessorPainter postprocessorPainter = new PostprocessorPainter();
        private Uri uri;

        public Builder() {
            postprocessorPainter.setFrame(0, 0, 1, 1);
        }

        public Builder load(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Builder with(List<? extends Overlay> overlays) {
            postprocessorPainter.overlays = overlays;
            return this;
        }

        public Builder font(Typeface typeface) {
            postprocessorPainter.typeface = typeface;
            return this;
        }

        public Builder debug() {
            postprocessorPainter.debug = true;
            return this;
        }

        public ImageRequest buildRequest() {
            return ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(postprocessorPainter)
                    .build();
        }

        public void into(DraweeView view) {
            PipelineDraweeController controller = (PipelineDraweeController)
                    Fresco.newDraweeControllerBuilder()
                            .setImageRequest(buildRequest())
                            .setOldController(view.getController())
                            .build();
            view.setController(controller);
        }

        public Builder frame(double left, double top, double right, double bottom) {
            postprocessorPainter.setFrame(left, top, right, bottom);
            return this;
        }
    }
}
