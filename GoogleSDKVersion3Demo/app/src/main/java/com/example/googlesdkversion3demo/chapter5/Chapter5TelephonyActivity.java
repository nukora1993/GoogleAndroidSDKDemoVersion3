package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.googlesdkversion3demo.R;

import java.util.ArrayList;
import java.util.List;

public class Chapter5TelephonyActivity extends AppCompatActivity {
    private static final String TAG="Chapter5Log";
    private TelephonyManager telephonyManager;
    private List<String> item=new ArrayList<>();
    private List<String> value=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_telephony);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},0);
            }

        }

        telephonyManager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        //sim卡状态
        Log.d(TAG,"sim state:"+telephonyManager.getSimState());
        //sim卡号
        Log.d(TAG,"sim serial:"+telephonyManager.getSimSerialNumber());
        //sim卡供货商
        Log.d(TAG,"sim vendor code:"+telephonyManager.getSimOperator());
        Log.d(TAG,"sim vendor name:"+telephonyManager.getSimOperatorName());
        //国家
        Log.d(TAG, "sim country:"+telephonyManager.getSimCountryIso());

    }
}
