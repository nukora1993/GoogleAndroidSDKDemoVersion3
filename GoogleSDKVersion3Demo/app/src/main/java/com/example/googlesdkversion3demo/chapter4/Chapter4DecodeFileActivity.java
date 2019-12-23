package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.io.File;

public class Chapter4DecodeFileActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Button mButton;
    private TextView mTextView;
    private String fileName= Environment.getExternalStorageDirectory()+"/wmm/cy.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_decode_file);

        mImageView =findViewById(R.id.image_view);

        mImageView.setImageResource(R.drawable.blog);
        mButton=findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTextView=findViewById(R.id.text_view);

                File f=new File(fileName);
                if(f.exists()){
                    Bitmap bm= BitmapFactory.decodeFile(fileName);
                    mImageView.setImageBitmap(bm);
                    mTextView.setText(fileName);
                }else{
                    mTextView.setText("can not find file "+fileName);
                }
            }
        });


    }
}
