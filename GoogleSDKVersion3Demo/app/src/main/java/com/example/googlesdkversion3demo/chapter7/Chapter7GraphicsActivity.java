package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;

import com.example.googlesdkversion3demo.R;

public class Chapter7GraphicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_graphics);
        MyView myView=new MyView(this);
        setContentView(myView);
    }

    private class MyView extends View {
        public MyView(Context context){
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            Paint paint=new Paint();
            //抗锯齿
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            //空心(就是只画轮廓，不填充里面)
            paint.setStyle(Paint.Style.STROKE);
            //外框宽度
            paint.setStrokeWidth(3);
            canvas.drawCircle(40,40,40,paint);
            canvas.drawRect(10,90,70,150,paint);

            RectF rectF=new RectF(10,220,70,250);
            canvas.drawOval(rectF,paint);

            Path path=new android.graphics.Path();
            path.moveTo(10,300);
            path.lineTo(70,330);
            path.lineTo(40,270);
            path.close();

            canvas.drawPath(path,paint);

            //实心
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);

            path=new android.graphics.Path();
            path.moveTo(10,300+100);
            path.lineTo(70,330+100);
            path.lineTo(40,270+100);

            canvas.drawPath(path,paint);

            //渐变色
            Shader shader=new LinearGradient(0,0,100,100,
                    new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},
                    null,Shader.TileMode.REPEAT);
            paint.setShader(shader);


            path=new android.graphics.Path();
            path.moveTo(10,300+100+100);
            path.lineTo(70,330+100+100);
            path.lineTo(40,270+100+100);
            canvas.drawPath(path,paint);

            //写字
            paint.setTextSize(24);
            canvas.drawText("Hello Paint",
                    240,50,paint);


            //除了上面的canvas.draw方式，还可以使用ShapeDrawable.draw
            ShapeDrawable sd=new ShapeDrawable(new RectShape());
            sd.getPaint().setShader(shader);
            sd.setBounds(20,20,100,100);
            sd.draw(canvas);




        }
    }
}
