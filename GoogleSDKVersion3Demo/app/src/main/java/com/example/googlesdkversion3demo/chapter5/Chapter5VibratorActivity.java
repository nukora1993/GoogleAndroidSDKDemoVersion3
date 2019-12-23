package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

public class Chapter5VibratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_vibrator);

        Vibrator mVibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        //0表示重复,-1表示不重复，且震动即使退出Activity也不会取消，必须调用cancel或者彻底退出Application
        mVibrator.vibrate(new long[]{100,10,100,1000},0);

        //给toast设置自定义的layout
        LinearLayout layout=new LinearLayout(this);

        Toast toast=Toast.makeText(this,"hello",Toast.LENGTH_LONG);
        View view=toast.getView();
        layout.setOrientation(LinearLayout.HORIZONTAL);


        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.blog);
        layout.addView(imageView);
        layout.addView(view);
        toast.setView(layout);
        toast.show();
    }
}
