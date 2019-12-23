package com.example.googlesdkversion3demo.chapter5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.googlesdkversion3demo.R;

public class Chapter5OrientationActivity extends AppCompatActivity {
    private static final String TAG="Chapter5Log";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_orientation);
        Button button=findViewById(R.id.button);

        //读取手机设置
        ContentResolver contentResolver=getContentResolver();
        String tmpS= Settings.System.getString(contentResolver,
                Settings.System.BLUETOOTH_ON);
        Log.d(TAG,tmpS);

        if(getRequestedOrientation()==-1){

        }

        //实测可以直接更改方向，而不用在manifest中配置screenOrientation属性
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRequestedOrientation()== ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.d(TAG,"onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }


}
