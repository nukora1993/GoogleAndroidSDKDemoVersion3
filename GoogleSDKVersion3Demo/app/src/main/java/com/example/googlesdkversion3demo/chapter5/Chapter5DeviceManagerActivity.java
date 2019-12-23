package com.example.googlesdkversion3demo.chapter5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

public class Chapter5DeviceManagerActivity extends AppCompatActivity {
    DevicePolicyManager devicePolicyManager;
    ComponentName deviceAdminReceiver;
    Button button1,button2,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_device_manager);

        devicePolicyManager=(DevicePolicyManager)getApplicationContext().getSystemService(DEVICE_POLICY_SERVICE);

        deviceAdminReceiver=new ComponentName(this,MyDeviceAdminReceiver.class);

        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);
        button3=findViewById(R.id.button_3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,deviceAdminReceiver);
                //启动设备管理器，添加自己的receiver
                startActivityForResult(intent,1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停用设备管理器
                devicePolicyManager.removeActiveAdmin(deviceAdminReceiver);
                updateButtonStates();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                //打开设置界面
                intent.setClass(Chapter5DeviceManagerActivity.this, Chapter5SettingActivity.class);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,deviceAdminReceiver);
                startActivity(intent);
            }
        });


    }

    private void updateButtonStates(){
        //是否已经激活
        boolean active=devicePolicyManager.isAdminActive(deviceAdminReceiver);
        if(active){
            button1.setEnabled(false);
            button2.setEnabled(true);
            button3.setEnabled(true);
        }else{
            button1.setEnabled(true);
            button2.setEnabled(false);
            button3.setEnabled(false);
        }

    }

    @Override
    protected void onResume() {
        updateButtonStates();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(resultCode){
            case RESULT_OK:
                Toast.makeText(this,"启动成功",Toast.LENGTH_SHORT).show();
                return;
                default:Toast.makeText(this,"取消启动",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
