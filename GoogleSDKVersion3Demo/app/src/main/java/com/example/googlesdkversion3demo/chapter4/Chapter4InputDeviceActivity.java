package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.example.googlesdkversion3demo.R;

public class Chapter4InputDeviceActivity extends AppCompatActivity {
    private static final String TAG="ChapterLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_input_device);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputDevice inputDevice=event.getDevice();
        int deviceId=event.getDeviceId();
        int source=event.getSource();

        Log.d(TAG,"Device name:"+inputDevice.getName());
        Log.d(TAG,"Device id:"+deviceId);
        Log.d(TAG,"Source of the event:"+source);
        Log.d(TAG,"X:"+event.getX()+",Y"+event.getY());

        




        return super.onTouchEvent(event);
    }
}
