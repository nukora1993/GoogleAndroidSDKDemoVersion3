package com.example.googlesdkversion3demo.chapter7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyMovingImageSurfaceView extends SurfaceView
implements SurfaceHolder.Callback {
    private MovingThread movingThread;
    private Bitmap bitmap;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private int currentX=50;
    private int currentY=50;
    private int destinationX=50;
    private int destinationY=50;

    int diffX=destinationX-currentX;
    int diffY=destinationY-currentY;

    int incrementX=-1;
    int incrementY=-1;

    public MyMovingImageSurfaceView(Context context, int resId){
        super(context);
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);

        paint=new Paint();

        //获得bitmap
        bitmap= BitmapFactory.decodeResource(getResources(),resId);


    }

    public MyMovingImageSurfaceView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    public MyMovingImageSurfaceView(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try{
            canvas.drawColor(Color.WHITE);
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onDraw(canvas);

        //差值
        diffX=destinationX-currentX;
        diffY=destinationY-currentY;

        //如果在左边，那么向左移，否则右移，incrementX是标志，1表示左移，0表示右移
        if(diffX<0){
            incrementX=1;
        }else if(diffX>0){
            incrementX=0;
        }else{
            incrementX=-1;
        }

        if(diffY<0){
            incrementY=1;
        }else if(diffY>0){
            incrementY=0;
        }else{
            incrementY=-1;
        }

        if(diffX!=0){
            if(incrementX==0){
                currentX++;
            }else if(incrementX==1){
                currentX--;
            }
        }

        if(diffY!=0){
            if(incrementY==0){
                currentY++;
            }else if(incrementY==1){
                currentY--;
            }
        }

        try{
            canvas.drawBitmap(bitmap,currentX,currentY,paint);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            destinationX=(int)event.getX();
            destinationY=(int)event.getY();

            diffX=destinationX-currentX;
            diffY=destinationY-currentY;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        movingThread=new MovingThread(holder,this);
        movingThread.setFlag(true);
        movingThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    class MovingThread extends Thread{
        private boolean flag;
        private SurfaceHolder holder;
        private MyMovingImageSurfaceView myMovingImageSurfaceView;

        public MovingThread(SurfaceHolder holder,MyMovingImageSurfaceView surfaceView){
            this.holder=holder;
            this.myMovingImageSurfaceView=surfaceView;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            Canvas canvas=null;
            while(flag){
                try{
                    canvas=holder.lockCanvas(null);
                    //非常特殊，surfaceview能够在子线程绘制
                    myMovingImageSurfaceView.onDraw(canvas);
                }catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if(canvas!=null){
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
