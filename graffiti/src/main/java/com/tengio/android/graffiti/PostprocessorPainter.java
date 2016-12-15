package com.tengio.android.graffiti;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

public class PostprocessorPainter extends BasePostprocessor {

    public static void build(SimpleDraweeView draweeView, Uri uri, List<? extends Overlay> overlays) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(new PostprocessorPainter(overlays))
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(draweeView.getController())
                        .build();
        draweeView.setController(controller);
    }

    private final List<? extends Overlay> overlays;

    public PostprocessorPainter(List<? extends Overlay> overlays) {
        this.overlays = overlays;
    }

    @Override
    public String getName() {
        return PostprocessorPainter.class.getSimpleName();
    }

    @Override
    public void process(Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(bitmap);
        if (overlays == null) {
            return;
        }
        for (Overlay overlay : overlays) {
            try {
                overlay.draw(canvas, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
