package com.tengio.android.graffiti.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tengio.android.fresco_zoomable.SimpleZoomableDraweeView;
import com.tengio.android.graffiti.HtmlTextOverlayPaint;
import com.tengio.android.graffiti.Overlay;
import com.tengio.android.graffiti.PostprocessorPainter;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.activity_main);

        SimpleZoomableDraweeView overlayView = (SimpleZoomableDraweeView) findViewById(R.id.example);
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.a_drunk_developer))
                .build();
        List<? extends Overlay> overlays = Arrays.asList(
                new HtmlTextOverlayPaint("THE HAT", 0.5, 0,
                                         0.2, 0.2, 0.5, 0.3),
                new HtmlTextOverlayPaint("THE WIG", 0.5, 0,
                                         0.2, 0.4, 0.5, 0.5),
                new HtmlTextOverlayPaint("A DEVELOPER THAT NEEDS YOUR HELP!", 0.5, 0,
                                         0.4, 0.7, 0.9, 0.8)
        );
        new PostprocessorPainter.Builder().load(uri).with(overlays).debug().into(overlayView);
    }
}
