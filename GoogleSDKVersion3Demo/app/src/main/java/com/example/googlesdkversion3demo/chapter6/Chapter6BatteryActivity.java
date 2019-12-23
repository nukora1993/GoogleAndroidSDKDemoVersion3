package com.example.googlesdkversion3demo.chapter6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.googlesdkversion3demo.R;

public class Chapter6BatteryActivity extends AppCompatActivity {
    private int level;
    private int scale;
    private Button button;
    private AlertDialog alertDialog;

    private BroadcastReceiver batteryInfoReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(Intent.ACTION_BATTERY_CHANGED.equals(action)){
                level=intent.getIntExtra("level",0);
                scale=intent.getIntExtra("scale",100);
                onBatteryInfoReceived(level,scale);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter6_battery);

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerReceiver(batteryInfoReceiver,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            }
        });

    }

    public void onBatteryInfoReceived(int level,int scale){
        alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle("title");
        alertDialog.setMessage(level*100/scale+"%");
        alertDialog.show();
    }



}
