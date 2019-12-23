package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter7MultiTouchActivity extends AppCompatActivity {
    private TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_multi_touch);

        textView1=findViewById(R.id.text_view_1);
        textView2=findViewById(R.id.text_view_2);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //触摸点数量,注意到onTouchEvent一旦有触摸事件就会触发而不管是什么触摸事件，pointerCount会得到现在屏幕上按着的
        textView2.setText(event.getPointerCount()+"");
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                textView1.setText("down");
                textView2.setText("0");
                break;
            case MotionEvent.ACTION_UP:
                textView1.setText("up");
                textView2.setText("0");
                break;
            case MotionEvent.ACTION_POINTER_1_UP:
                textView1.setText("pointer1 up");
                break;
            case MotionEvent.ACTION_POINTER_1_DOWN:
                textView1.setText("pointer1 down");
                break;
            case MotionEvent.ACTION_POINTER_2_UP:
                textView1.setText("pointer2 up");
                break;
            case MotionEvent.ACTION_POINTER_2_DOWN:
                textView1.setText("pointer2 down");
                break;

        }


        return super.onTouchEvent(event);
    }
}
