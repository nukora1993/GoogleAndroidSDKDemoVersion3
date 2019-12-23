package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.googlesdkversion3demo.R;

public class Chapter4ImageScaleActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Button mButton1;
    private Button mButton2;
    private LinearLayout linearLayout;
    private Bitmap bmap;

    private int id=0;
    private int displayWidth;
    private int displayHeight;
    private float scaleWidth=1;
    private float scaleHeight=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_image_scale);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        displayWidth=dm.widthPixels;
        displayHeight=dm.heightPixels;

        bmap= BitmapFactory.decodeResource(getResources(),R.drawable.blog);

        mImageView=findViewById(R.id.image_view);
        linearLayout=findViewById(R.id.layout);
        mButton1=findViewById(R.id.button_1);
        mButton2=findViewById(R.id.button_2);

        mImageView.setImageBitmap(bmap);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                small();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                big();
            }
        });
    }


    private void small(){
        int bmapWidth=bmap.getWidth();
        int bmapHeight=bmap.getHeight();

        double scale=0.8;
        scaleWidth=(float)(scaleWidth*scale);
        scaleHeight=(float)(scaleHeight*scale);

        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap resizeBmap=Bitmap.createBitmap(bmap,0,0,bmapWidth,bmapHeight,matrix,true);

        //第一次按，删除默认的(他的逻辑是重新创建一个新的ImageView来存放缩放后的图片，但是为什么不直接替换图片呢？)
        //他的说法是不允许动态修改长宽，但是实际测试使用wrapContent可以正常动态修改
//        if(id==0){
//            linearLayout.removeView(mImageView);
//        }else{
//            linearLayout.removeView(findViewById(id));
//        }

//        id++;
//        ImageView imageView=new ImageView(this);
//        imageView.setId(id);
        mImageView.setImageBitmap(resizeBmap);
//        linearLayout.addView(imageView);
//        setContentView(linearLayout);

        mButton2.setEnabled(true);
    }

    private void big(){
        int bmapWidth=bmap.getWidth();
        int bmapHeight=bmap.getHeight();

        double scale=1.25;
        scaleWidth=(float)(scaleWidth*scale);
        scaleHeight=(float)(scaleHeight*scale);

        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap resizeBmap=Bitmap.createBitmap(bmap,0,0,bmapWidth,bmapHeight,matrix,true);

//        if(id==0){
//            linearLayout.removeView(mImageView);
//        }
//        else{
//            linearLayout.removeView(findViewById(id));
//        }
//        id++;
//        ImageView imageView=new ImageView(this);
        mImageView.setId(id);
        mImageView.setImageBitmap(resizeBmap);
//        setContentView(linearLayout);

        if(scaleWidth*scale*bmapWidth>displayWidth||scaleHeight*scale*bmapHeight>displayHeight){
            mButton2.setEnabled(false);
        }
    }
}
