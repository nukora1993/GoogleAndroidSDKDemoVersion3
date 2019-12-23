package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.googlesdkversion3demo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chapter5PackageManagerActivity extends AppCompatActivity {
    private static final String TAG="Chapter5Log";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_package_manager);

        List<PackageInfo> packageInfoList;
        packageInfoList=getPackageManager().getInstalledPackages(
                PackageManager.GET_ACTIVITIES
        );

        for (PackageInfo packageInfo:packageInfoList){
            Log.d(TAG,packageInfo.packageName+":"+packageInfo.lastUpdateTime+":"+packageInfo.applicationInfo.dataDir);
        }
    }
}
