package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.googlesdkversion3demo.R;

public class Chapter7MoveImageActivity extends AppCompatActivity {
    private ImageView imageView;
    private int width,height,defaultX,defaultY;
    private float mX,mY;
    private int screenX,screenY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_move_image);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenX=displayMetrics.widthPixels;
        screenY=displayMetrics.heightPixels;

        width=100;
        height=100;
        imageView=findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.blog);

        //恢复imageView位置
        restoreButton();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreButton();
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();

        try{
            picMove(x,y);

        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private void picMove(float x,float y){

        //接触点减去一半
        mX=x-(width/2);
        mY=y-(height/2);

        //mX+width=x+(width/2)也就是接触点加一半的宽度
        if((mX+width)>screenX){
            mX=screenX-width;

        }else if(mX<0){
            mX=0;
        }else if((mY+height)>screenY){
            mY=screenY-height;
        }else if(mY<0){
            mY=0;
        }

        imageView.setLayoutParams(
                new AbsoluteLayout.LayoutParams(width,height,(int)mX,(int)mY)
        );


    }

    private void restoreButton(){

        defaultX=((screenX-width)/2);
        defaultY=((screenY-height)/2);

        imageView.setLayoutParams(
                new AbsoluteLayout.LayoutParams(width,height,defaultX,defaultY)
        );

    }
}
