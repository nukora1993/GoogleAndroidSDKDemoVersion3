package com.example.googlesdkversion3demo.chapter3;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {
    //返回一个从右向左的平移动画(滑进）
    public static Animation inFromRightAnimation(){
        Animation inFromRight=new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,1.0f,
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,0.0f
        );

        inFromRight.setDuration(350);
        inFromRight.setInterpolator(new AccelerateDecelerateInterpolator());
        return inFromRight;
    }

    //从右向左的set out事件（滑出）
    public static Animation outToLeftAnimation(){
        Animation outToLeft=new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,-1.0f,
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,0.0f
        );
        outToLeft.setDuration(350);
        outToLeft.setInterpolator(new AccelerateDecelerateInterpolator());
        return outToLeft;
    }

    public static Animation inFromLeftAnimation(){
        Animation inFromLeft=new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,-1.0f,
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,0.0f
        );
        inFromLeft.setDuration(350);
        inFromLeft.setInterpolator(new AccelerateDecelerateInterpolator());
        return inFromLeft;
    }

    public static Animation outToRightAnimation(){
        Animation outToRight=new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,1.0f,
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,0.0f
        );
        outToRight.setDuration(350);
        outToRight.setInterpolator(new AccelerateDecelerateInterpolator());
        return outToRight;
    }


}
