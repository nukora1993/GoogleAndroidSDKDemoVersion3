package com.example.googlesdkversion3demo.chapter5;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.googlesdkversion3demo.R;

public class HackerView extends LinearLayout {
    private Chapter5ToastUIActivity activity;

    public HackerView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public void setActivityToSpoof(Chapter5ToastUIActivity activity){
        this.activity=activity;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //调整layout，使得其和原来的画面一致,spoof是模仿的意思
        spoofLayout(
                findViewById(R.id.hack_image_view_1),
                activity.findViewById(R.id.image_view_1)
        );

        spoofLayout(
                findViewById(R.id.hack_image_view_2),
                activity.findViewById(R.id.image_view_2)
        );

        spoofLayout(
                findViewById(R.id.hack_button),
                activity.findViewById(R.id.button)
        );

    }

    private void spoofLayout(View spoof,View original){
        final int[] globalPos=new int[2];
        //获得伪造控件在屏幕的位置
        getLocationOnScreen(globalPos);
        int x=globalPos[0];
        int y=globalPos[1];

        //获得原控件在屏幕的位置
        original.getLocationOnScreen(globalPos);


        x=globalPos[0]-x;
        y=globalPos[1]-y;

        //放到正确的位置
        spoof.layout(
                x,y,x+original.getWidth(),y+original.getHeight()
        );
    }
}
