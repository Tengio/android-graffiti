package com.tengio.android.graffiti.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tengio.android.graffiti.PostprocessorPainter;
import com.tengio.android.graffiti.TextOverlayPaint;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.activity_main);

        SimpleDraweeView overlayView = (SimpleDraweeView) findViewById(R.id.example);
        overlayView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.a_drunk_developer))
                .build();
        PostprocessorPainter.build(overlayView, uri, Arrays.asList(
                new TextOverlayPaint("THE HAT", 0.51815585475323445, 0, 0.712334552075814, 0.90100593049157859, 0.2161676595217783, 0.060847496164525172),
                new TextOverlayPaint("THE FAKE HAPPY SMILE", 0.51815585475323445, 0, 0.212334552075814, 0.50100593049157859, 0.2161676595217783, 0.060847496164525172),
                new TextOverlayPaint("A DEVELOPER THAT NEED YOUR HELP!", 0.51815585475323445, 0, 0.612334552075814, 0.20100593049157859, 0.2161676595217783, 0.060847496164525172)));
    }
}
