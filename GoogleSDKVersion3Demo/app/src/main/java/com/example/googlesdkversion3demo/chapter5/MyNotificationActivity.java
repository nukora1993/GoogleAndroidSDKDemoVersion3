package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.os.Bundle;

import com.example.googlesdkversion3demo.R;

public class MyNotificationActivity extends AppCompatActivity {
    private static final String TAG="Chapter5Log";
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);

        notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        try{
            Bundle bundle=getIntent().getExtras();
            String strNotificationId=bundle.getString("MY_NOTIFICATION_ID");

            notificationManager.cancel(Integer.parseInt(strNotificationId));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
