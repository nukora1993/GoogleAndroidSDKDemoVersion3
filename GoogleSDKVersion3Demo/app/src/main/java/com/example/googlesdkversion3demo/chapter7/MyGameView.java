package com.example.googlesdkversion3demo.chapter7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyGameView extends View {
    private static final String TAG="MyGameView";

    private boolean gameStart=false;
    private boolean gamePass=false;
    private boolean gameOver=false;
    private float tubeWidth=80;
    private float edge=10;
    private int w=0;
    private int h=0;
    private float r=40;
    private float d=0;
    private float touchX=0;
    private float touchY=0;
    private float radius=30;
    private float startX_R=0;
    private float startX_L=0;
    private float startY_T=0;
    private float startY_B=0;
    private float endX_R=0;
    private float endX_L=0;
    private float endY_T=0;
    private float endY_B=0;

    public MyGameView(Context context,int width,int height,float density){
        super(context);
        w=width;
        h=height-48;
        d=density;
        r=r*d;
        tubeWidth=tubeWidth*d;
        radius=radius*d;

        startX_R=edge+r*2;
        startX_L=edge;
        startY_T=edge;
        startY_B=edge+r*2;

        endX_R=w-edge;
        endX_L=w-edge-r*2;
        endY_T=h-edge-r*2;
        endY_B=h-edge;



    }

    @Override
    public void draw(Canvas canvas) {
//        Log.d(TAG,"draw");

        //蓝色画笔，绘制触摸点
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);

        //黑色，画底图
        Paint paint1=new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.BLACK);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(3);
        paint1.setTextSize(40);

        //红色，写字
        Paint paint2=new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.RED);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setTextSize(48);

        //游戏起点,画圆
        canvas.drawCircle(edge+r,edge+r,r,paint1);
        canvas.drawText("S",r+edge-20,r+edge+20,paint1);

        //绘制游戏终点
        canvas.drawCircle(edge+r,edge+r,r,paint1);
        canvas.drawText("E",w-r-edge-20,h-r-edge+20,paint1);

        //绘制底图
        canvas.drawLine(edge+r*2,edge,w/2+tubeWidth/2,edge,paint1);
        canvas.drawLine(
                w/2+tubeWidth/2,edge,w/2+tubeWidth/2,h-edge-tubeWidth,paint1
        );

        canvas.drawLine(
                w/2+tubeWidth/2,h-edge-tubeWidth,w-edge-r*2,h-edge-tubeWidth,paint1
        );

        canvas.drawLine(
                edge+r*2,edge+r*2,w/2-tubeWidth/2,edge+r*2,paint1
        );

        canvas.drawLine(
                w/2-tubeWidth/2,edge+r*2,w/2-tubeWidth/2,h-edge,paint1
        );

        canvas.drawLine(
                w/2-tubeWidth/2,h-edge,w-edge-r*2,h-edge,paint1
        );

        //在触摸的地方绘制圆形
        if(gameStart){
            canvas.drawCircle(touchX,touchY,radius,paint);
        }

        if(gamePass){
            canvas.drawText("GAME PASS!",edge,h-edge,paint2);
        }

        if(gameOver){
            canvas.drawText("GAME OVER!",edge,h-edge,paint2);
        }


        super.draw(canvas);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        touchX=event.getX();
        touchY=event.getY();

        Log.d(TAG,"onTouch:"+touchX+","+touchY);

        float touchR=touchX+radius;
        float touchL=touchX-radius;
        float touchT=touchY-radius;
        float touchB=touchY+radius;

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            //判断是否在起点范围
            if((touchR<startX_R)&&(touchL>startX_L)&&(touchT>startY_T)&&(touchB<startY_B)){
                gameStart=true;
                gamePass=false;
                gameOver=false;
            }
            else if(event.getAction()==MotionEvent.ACTION_UP){
                gameStart=false;
            }
            else if(event.getAction()==MotionEvent.ACTION_MOVE){
                if(gameStart){
                    //是否在安全范围内
                    if(
                            ((touchR<(w/2+tubeWidth/2))&&(touchL>edge)&&
                            (touchT>edge)&&(touchB<edge+r*2))
                        || ((touchR<(w/2+tubeWidth/2))&&(touchL>(w/2-tubeWidth/2))&&
                                    (touchT>edge)&&(touchB<h-edge))
                        || ((touchR<w-edge)&&(touchL>(w/2-tubeWidth/2))&&
                                    (touchT>h-edge-r*2)&&(touchB<h-edge))
                    ){
                        //判断是否在终点范围
                        if((touchR<endX_R)&&(touchL>endX_L)&&(touchB<endY_B)&&(touchT>endY_T)){
                            gameStart=false;
                            gamePass=true;
                            gameOver=false;
                        }
                    }else{
                        gameStart=false;
                        gamePass=false;
                        gameOver=true;
                    }
                }
            }
        }
        invalidate();
        return true;
    }
}
