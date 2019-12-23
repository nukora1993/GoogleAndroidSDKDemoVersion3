package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter4ImageRotateActivity extends AppCompatActivity {
    private Button mButton1;
    private Button mButton2;
    private TextView mTextView;
    private ImageView mImageView;

    private int scaleTimes;
    private int scaleAngle;

    private int widthOrig;
    private int heightOrig;

    private Bitmap mySourceBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_image_rotate);

        mButton1=findViewById(R.id.button_1);
        mButton2=findViewById(R.id.button_2);

        mTextView=findViewById(R.id.text_view);
        mImageView=findViewById(R.id.image_view);

        scaleTimes=1;
        scaleAngle=1;

        mySourceBmp= BitmapFactory.decodeResource(getResources(),R.drawable.blog);

        widthOrig=mySourceBmp.getWidth();
        heightOrig=mySourceBmp.getHeight();

        mImageView.setImageBitmap(mySourceBmp);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleAngle--;
                if(scaleAngle<-5){
                    scaleAngle=-5;
                }

                rotateImage(scaleAngle);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleAngle++;
                if(scaleAngle>5){
                    scaleAngle=5;
                }
                rotateImage(scaleAngle);
            }
        });


    }

    private void rotateImage(int scaleAngle){
        int newWidth=widthOrig*scaleTimes;
        int newHeight=heightOrig*scaleTimes;

        //缩放比例
        float scaleWidth=((float)newWidth)/widthOrig;
        float scaleHeight=((float)newHeight)/heightOrig;

        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);

        matrix.setRotate(5*scaleAngle);

        Bitmap resizedBimap=Bitmap.createBitmap(
                mySourceBmp,0,0,widthOrig,heightOrig,matrix,true
        );

        BitmapDrawable newBitmapDrawable=new BitmapDrawable(resizedBimap);
        mImageView.setImageDrawable(newBitmapDrawable);
        mTextView.setText(Integer.toString(5*scaleAngle));

        //layoutparam,应当该父child addview时，应当设置为父的param而不是child的param


    }
}
