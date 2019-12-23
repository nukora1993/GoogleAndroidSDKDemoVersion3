package com.example.googlesdkversion3demo.chapter3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

import com.example.googlesdkversion3demo.R;

//ViewFlipper实现了类似一个横向的listview的效果，并且划入和画出可以设置动画效果
public class Chapter3ViewFlipperActivity extends Activity {
    private static final String TAG="Chapter3Log";
    private ViewFlipper myViewFlipper;
    private float oldTouchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chapter3_view_flipper);

        myViewFlipper=findViewById(R.id.my_view_flipper_1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                oldTouchValue=event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX=event.getX();
                //向右滑动
                if(oldTouchValue<currentX){
                    //set In起使动画
                    myViewFlipper.setInAnimation(
                            AnimationHelper.inFromLeftAnimation()
                    );
                    //set Out起使动画
                    myViewFlipper.setOutAnimation(
                            AnimationHelper.outToRightAnimation()
                    );
                    myViewFlipper.showNext();
                }

                if(oldTouchValue>currentX){
                    myViewFlipper.setInAnimation(
                            AnimationHelper.inFromRightAnimation()
                    );

                    myViewFlipper.setOutAnimation(
                            AnimationHelper.outToLeftAnimation()
                    );
                    myViewFlipper.showPrevious();
                }
                break;
            default:break;



        }
        return super.onTouchEvent(event);
    }
}
