package com.example.googlesdkversion3demo.chapter7;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class CustomButton extends Button {
    private Animation buttonAnimation;

    public CustomButton(Context context){
        super(context);

        buttonAnimation= AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_out);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startAnimation(buttonAnimation);
                return false;
            }
        });
    }

    public CustomButton(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public CustomButton(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }
}
