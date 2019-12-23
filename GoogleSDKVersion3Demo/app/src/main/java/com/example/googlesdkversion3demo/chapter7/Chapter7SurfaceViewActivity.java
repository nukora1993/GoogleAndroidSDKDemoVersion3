package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.googlesdkversion3demo.R;

public class Chapter7SurfaceViewActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private MyMovingImageSurfaceView myMovingImageSurfaceView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_surface_view);

        linearLayout=findViewById(R.id.linear_layout);

        myMovingImageSurfaceView=new MyMovingImageSurfaceView(getApplicationContext(),android.R.drawable.presence_audio_online);
        linearLayout.addView(myMovingImageSurfaceView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

    }
}
