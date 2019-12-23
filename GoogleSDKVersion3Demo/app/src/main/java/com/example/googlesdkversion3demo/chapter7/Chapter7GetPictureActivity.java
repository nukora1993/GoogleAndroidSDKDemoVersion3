package com.example.googlesdkversion3demo.chapter7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.googlesdkversion3demo.R;

import java.io.FileNotFoundException;

public class Chapter7GetPictureActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_get_picture);

        imageView=findViewById(R.id.image_view);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            Uri uri=data.getData();
            ContentResolver contentResolver=getContentResolver();
            try{
                //除了query，contentResolver还能直接打开一个输入流
                Bitmap bitmap= BitmapFactory.decodeStream(
                        contentResolver.openInputStream(uri)
                );
                imageView.setImageBitmap(bitmap);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


