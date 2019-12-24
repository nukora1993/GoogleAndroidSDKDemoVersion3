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
//问题根源：继承的View默认clickable为false，必须要设置其clickable为true
//事件分发机制：假设从ViewGroup收到事件，调用自己的dispatch，那么首先决定是否拦截，如果拦截，那么会调用ViewGroup自己的onTouchEvent
//如果不拦截，则调用child的dispatch
//一旦拦截，后序的事件都会直接交给ViewGroup
//View的onTouchEvent默认会消耗，除非是不可点击的，而一旦不消耗down，那么事件不在交给他处理

//由于自定义view默认是不可点击的，而且直接返回了super.onTouchEvent，导致在收到down之后系统不再把事件交给他处理，也就无法捕获到move了
//解决方法，要么设置Clickable或者longClickable有一个为true，要么在onTouchEvent返回true


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

        Log.d("my game view","my game view is clickable:"+myGameView.isClickable()+","+myGameView.isLongClickable());

//        myGameView.setClickable(true);

        linearLayout.addView(myGameView,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));



        Log.d("my game view","my game view is clickable:"+myGameView.isClickable()+","+myGameView.isLongClickable());

        //让linearlayout
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("linear_layout",event.toString());
                Log.d("linear_layout",linearLayout.isClickable()+"");

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
