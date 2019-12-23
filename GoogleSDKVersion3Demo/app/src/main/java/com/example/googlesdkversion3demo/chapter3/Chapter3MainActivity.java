package com.example.googlesdkversion3demo.chapter3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter3MainActivity extends AppCompatActivity {
    private static final String TAG="Chapter3Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter3_main);
        Drawable drawable=getResources().getDrawable(R.drawable.ic_launcher_background);
        TextView textView1=(TextView)findViewById(R.id.text_view_1);
        textView1.setBackgroundDrawable(drawable);

        //获得屏幕参数
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Log.d(TAG,"屏幕分辨率"+dm.widthPixels+"x"+dm.heightPixels);
        //设置字体(assets在main目录，和res目录平级。必须在asset文件夹下创建fonts文件夹，并且放入ttf文件)
//        textView1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/HandmadeTypewriter.ttf"));
        //使用内置字体
        textView1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));

        
    }
}
