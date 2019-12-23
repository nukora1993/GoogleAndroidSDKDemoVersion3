package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;

import com.example.googlesdkversion3demo.R;

import java.io.File;

public class Chapter7ExifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_exif);

        
        try{
            ExifInterface exifInterface=new ExifInterface(Environment.getExternalStorageDirectory()+"/1.jpg");
            System.out.println();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
