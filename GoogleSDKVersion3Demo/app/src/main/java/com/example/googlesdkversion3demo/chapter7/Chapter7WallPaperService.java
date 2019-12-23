package com.example.googlesdkversion3demo.chapter7;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.googlesdkversion3demo.R;

//开启动态壁纸的方式每个手机不一样，S10的方式是进入壁纸->查看所有->右上角选择动态壁纸->选择该service
public class Chapter7WallPaperService extends WallpaperService {
    private static final String TAG="WallPaperService";
    private final Handler handler=new Handler();


    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }


    class MyEngine extends Engine{
        private final Paint paint=new Paint();
        private float centerX;
        private float centerY;
        private boolean visible;
        private int touchX;
        private int touchY;

        private Bitmap bm1;
        private Bitmap bm2;
        private Bitmap bm3;
        private Bitmap bm4;

        //x表示显示那张图片
        private int x=0;

        //调用了draw函数
        private final Runnable myDraw=new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            bm1= BitmapFactory.decodeResource(getResources(), android.R.drawable.presence_audio_online);
            bm2= BitmapFactory.decodeResource(getResources(), android.R.drawable.presence_audio_busy);
            bm3= BitmapFactory.decodeResource(getResources(), android.R.drawable.presence_audio_away);
            bm4= BitmapFactory.decodeResource(getResources(), android.R.drawable.presence_invisible);
            //要让动态壁纸响应触摸事件，必须设置为true
            setTouchEventsEnabled(true);
            super.onCreate(surfaceHolder);
        }

        //当桌面可见性发生变化
        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible=visible;
            if(visible){
                draw();
            }else{
                //不可见时停止myDraw
                handler.removeCallbacks(myDraw);
            }
            super.onVisibilityChanged(visible);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //保存屏幕中点
            centerX=width/2;
            centerY=height/2;
            draw();

            super.onSurfaceChanged(holder, format, width, height);
        }

        //动态壁纸可以响应触摸事件


        @Override
        public void onTouchEvent(MotionEvent event) {
            Log.d(TAG,"onTouch");
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                touchX=(int)event.getX();
                touchY=(int)event.getY();
                drawTouch();
            }
            super.onTouchEvent(event);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(myDraw);
        }

        private void drawTouch(){
            Paint paint=new Paint();
            final SurfaceHolder holder=getSurfaceHolder();
            Canvas canvas=holder.lockCanvas();
            if(canvas!=null){
                canvas.drawBitmap(bm1,touchX-90,touchY-90,paint);
            }
            holder.unlockCanvasAndPost(canvas);
        }

        private void draw(){
            Log.d(TAG,"draw x:"+x);
            //wallpaperService中关联了surfaceHolder
            final SurfaceHolder holder=getSurfaceHolder();
            Canvas canvas=holder.lockCanvas();
            //绘制之前清除canvas内容，不然之前的绘制会保留
            canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
            if(canvas!=null){
                if(x==0){
                    canvas.drawBitmap(bm1,centerX-90,centerY-90,paint);
                }
                else if(x==1){
                    canvas.drawBitmap(bm2,centerX+90,centerY-90,paint);
                }else if(x==2){
                    canvas.drawBitmap(bm3,centerX-90,centerY-90,paint);
                }else{
                    canvas.drawBitmap(bm4,centerX-90,centerY-90,paint);
                }
                x=(x+1)%4;
                holder.unlockCanvasAndPost(canvas);
            }

            //停止上一次的绘制
            handler.removeCallbacks(myDraw);


            //如果可见，那么延迟1s开始
            if(visible){
                Log.d(TAG,"post delayed");
                handler.postDelayed(myDraw,1000);
            }

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
