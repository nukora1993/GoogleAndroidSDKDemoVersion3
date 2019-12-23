package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.googlesdkversion3demo.R;

public class Chapter7GestureDetectorActivity extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;
    private int bwidth=0;
    private int bheight=0;
    private int width=0;
    private int height=0;
    //上次滑动的坐标
    private int pointX=0;
    private int pointY=0;
    private GestureDetector detector;
    private MyGestureListener gestureListener;

    class MyGestureListener implements GestureDetector.OnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //distanceX向左向上为正，所以需要向左上方滑动，pointXY记录当前的点
            if(pointX+distanceX>=0){
                //如果还能够移动
                if(pointX+distanceX<bwidth) {
                    pointX += distanceX;
                }
            }else{
                pointX=0;
            }

            if(pointY+distanceY>=0){
                if((pointY+distanceY)<bheight){
                    pointY+=distanceY;
                }
            }else {
                pointY=0;
            }

            if(distanceX!=0&&distanceY!=0){
                Bitmap newBitmap=Bitmap.createBitmap(bitmap,pointX,pointY,bwidth-pointX,bheight-pointY);
                imageView.setImageBitmap(newBitmap);
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //很奇怪，在pixel2上，可以取消标题，但状态栏还在，再s10上，标题栏还在，状态栏不见
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_chapter7_gesture_detector);

        //不知道为什么，pixel2模拟器是1080*1794，但是实际应该是1080*1920
        width=getWindowManager().getDefaultDisplay().getWidth();
        height=getWindowManager().getDefaultDisplay().getHeight();

        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.blog);
        bwidth=bitmap.getWidth();
        bheight=bitmap.getHeight();

        imageView=findViewById(R.id.image_view);

        //从指定点取出一部分图片
        Bitmap newBitmap=Bitmap.createBitmap(bitmap,pointX,pointY,bwidth,bheight);

        imageView.setImageBitmap(newBitmap);

        gestureListener=new MyGestureListener();
        detector=new GestureDetector(this,gestureListener);



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(detector.onTouchEvent(event)){
            return detector.onTouchEvent(event);
        }else{
            return super.onTouchEvent(event);
        }
    }
}
