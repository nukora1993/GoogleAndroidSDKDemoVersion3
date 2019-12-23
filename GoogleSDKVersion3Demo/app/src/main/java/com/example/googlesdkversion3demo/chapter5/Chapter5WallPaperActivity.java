package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.googlesdkversion3demo.R;

import java.io.IOException;

public class Chapter5WallPaperActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_wall_paper);

        button=new Button(this);
        addContentView(button,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    clearWallpaper();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //设置壁纸，实测在S10不行，因为各个厂家基本都有自己的桌面管理，所以只能去参考厂家的设置壁纸api
    @Override
    public void clearWallpaper() throws IOException {

//        setWallpaper(BitmapFactory.decodeResource(getResources(),R.drawable.blog));
//        super.clearWallpaper();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            findViewById(android.R.id.content).setBackground(getWallpaper());
        }
    }
}
