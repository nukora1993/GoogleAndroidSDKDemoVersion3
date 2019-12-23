package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

public class Chapter5WifiActivity extends AppCompatActivity {
    private CheckBox checkBox;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_wifi);

        checkBox=findViewById(R.id.check_box);
        //需要使用applicationContext否则会导致泄漏(不释放？)
        wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()){

            if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED){
                checkBox.setChecked(true);
                checkBox.setText("关闭wifi");

            }else {
                checkBox.setChecked(false);
                checkBox.setText("wifi未打开");

            }
        }else{
            checkBox.setChecked(false);
            checkBox.setText("wifi未开启");
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()==false){
                    try{
                        if(wifiManager.isWifiEnabled()){
                            if(wifiManager.setWifiEnabled(false)){
                                Toast.makeText(Chapter5WifiActivity.this,"已关闭wifi",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Chapter5WifiActivity.this,"关闭wifi失败",Toast.LENGTH_SHORT).show();
                            }
                        }else{

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}
