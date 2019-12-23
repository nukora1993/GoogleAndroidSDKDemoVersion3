package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.googlesdkversion3demo.R;

//存在问题，MOVE事件不能被MyGameView捕获
public class Chapter7GameActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private MyGameView myGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//        );
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_chapter7_game);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //屏幕宽高
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        //密度,以160dpi为1
        float density=dm.density;

        linearLayout=findViewById(R.id.linear_layout);




        myGameView=new MyGameView(this,width,height,density);
        linearLayout.addView(myGameView,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));



        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("linear_layout",event.toString());
                return false;
            }
        });






    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("main_activity",event.toString());
        return super.onTouchEvent(event);
    }
}
