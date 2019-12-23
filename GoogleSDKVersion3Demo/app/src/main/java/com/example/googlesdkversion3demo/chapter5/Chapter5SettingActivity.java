package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.googlesdkversion3demo.R;

public class Chapter5SettingActivity extends AppCompatActivity {
    DevicePolicyManager devicePolicyManager;
    ComponentName deviceAdminReceiver;
    boolean active;
    Button button1,button2,button3,button4;
    EditText editText1,editText2;

    //deviceManager可以设置锁屏密码（如果已经存在密码，则不能直接设置）、设置锁屏时间、锁屏等
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_setting);

        devicePolicyManager=(DevicePolicyManager)getSystemService(
                DEVICE_POLICY_SERVICE
        );

        deviceAdminReceiver=getIntent().getParcelableExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN);
        active=devicePolicyManager.isAdminActive(deviceAdminReceiver);

        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);
        button3=findViewById(R.id.button_3);
        button4=findViewById(R.id.button_4);

        editText1=findViewById(R.id.edit_text1);
        editText2=findViewById(R.id.edit_text2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active){
                    //锁屏
                    devicePolicyManager.lockNow();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=0;
                String numStr=""+editText1.getText();
                if(!TextUtils.isEmpty(numStr)){
                    num=Integer.parseInt(numStr);
                }
                //设置密码错误次数限制
                if(active&&num>0){
                    devicePolicyManager.setMaximumFailedPasswordsForWipe(
                            deviceAdminReceiver,num
                    );
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active){
                    AlertDialog.Builder builder=new AlertDialog.Builder(Chapter5SettingActivity.this);
                    builder.setMessage("将会删除手机数据，确定？");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //危险操作，删除手机数据
//                            devicePolicyManager.wipeData(0);
                        }
                    });
                    builder.show();
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long timeSec=0;
                String timeStr=""+editText2.getText();
                if(!TextUtils.isEmpty(timeStr)){
                    timeSec=Long.parseLong(timeStr);
                }
                if(active){
                    long timeMs=1000*timeSec;
                    //设置锁屏时间
                    devicePolicyManager.setMaximumTimeToLock(deviceAdminReceiver,timeSec);
                }
            }
        });
    }
}
