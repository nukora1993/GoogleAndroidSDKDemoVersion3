package com.example.googlesdkversion3demo.chapter5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.example.googlesdkversion3demo.R;

public class Chapter5ProgressNotificationActivity extends AppCompatActivity {
    private static final String TAG="Chapter5Log";
    private Button button1,button2,button3,button4;
    private NotificationManager notificationManager;

    private static final int DOWNLOADING=0x001;
    private static final int DOWNLOADED=0x002;
    private boolean isDownloading=false;
    private int intDownloadProgress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_progress_notification);

        notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);
        button3=findViewById(R.id.button_3);
        button4=findViewById(R.id.button_4);
        initButtonListener();
    }

    private void initButtonListener(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDownloading){
                    intDownloadProgress=0;
                    isDownloading=true;
                    Thread thread=new Thread(new DownloadFileRunnable());
                    thread.start();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificatioAlertOnlySound(Chapter5ProgressNotificationActivity.this,
                        notificationManager,R.drawable.ic_launcher_foreground,"Tickets","Title","My Body",0);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificatioAlertOnlySound(Chapter5ProgressNotificationActivity.this,
                        notificationManager,R.drawable.ic_launcher_foreground,"Tickets2","Title2","My Body2",2);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificatioAlertOnlySound(Chapter5ProgressNotificationActivity.this,
                        notificationManager,R.drawable.ic_launcher_foreground,"Tickets3","Title3","My Body3",3);
            }
        });
    }

    private void showNotificatioAlertOnlySound(
            Context context,NotificationManager notificationManager,int iconRes,String strTicket,String strTitle,
            String strBody,int identifier
    ){
//        Notification notification=new Notification();
//        notification.icon=iconRes;
//        notification.tickerText=strTicket;
//        notification.defaults=Notification.DEFAULT_SOUND;

        Intent notificationIntent=new Intent(Chapter5ProgressNotificationActivity.this,MyNotificationActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("MY_NOTIFICATION_ID",Integer.toString(identifier));
        notificationIntent.putExtras(bundle);


        PendingIntent pi=PendingIntent.getActivity(Chapter5ProgressNotificationActivity.this,
                identifier,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent notificationFullScreenIntent=new Intent(Chapter5ProgressNotificationActivity.this,FullScreenIntentActivity.class);
        notificationFullScreenIntent.putExtras(bundle);
        PendingIntent pi2=PendingIntent.getActivity(Chapter5ProgressNotificationActivity.this,
                identifier,notificationFullScreenIntent,PendingIntent.FLAG_UPDATE_CURRENT);

//        notification.fullScreenIntent=pi2;
        NotificationChannel notificationChannel=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("1", "name", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
            Notification.Builder builder=new Notification.Builder(Chapter5ProgressNotificationActivity.this,
                    "1");
            builder.setTicker(strTicket)
                    .setSmallIcon(iconRes)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle(strTitle)
                    .setContentText(strBody)
                    .setContentIntent(pi)
                    //这里是悬浮通知，但是好像不能打开设定的Activity
                    .setFullScreenIntent(pi2,true);

            Notification notification=builder.build();
            notificationManager.notify(identifier,notification);
        }



    }

    class DownloadFileRunnable implements Runnable{
        @Override
        public void run() {
            Message m=new Message();
            if(isDownloading){
                m.what=DOWNLOADING;
                m.obj="Progress:"+intDownloadProgress*25+"%";
                intDownloadProgress++;
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                m.what=DOWNLOADED;
                m.obj="File Downloaded";
            }
            downloadHandler.sendMessage(m);
        }
    }

    private Handler downloadHandler=new Handler(){
        Thread thread=null;
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case DOWNLOADING:
                    thread=new Thread(new DownloadFileRunnable());
                    if(intDownloadProgress<5){
                        showNotificatioAlertOnlySound(Chapter5ProgressNotificationActivity.this,notificationManager,
                                R.drawable.ic_launcher_background,"Tickets1","Title1",
                                msg.obj.toString(),1);
                    }else{
                        isDownloading=false;
                    }
                    thread.start();
                    break;
                case DOWNLOADED:
                    isDownloading=false;
                    showNotificatioAlertOnlySound(Chapter5ProgressNotificationActivity.this,notificationManager,
                            R.drawable.ic_launcher_foreground,"Tickets1","Title1",
                            msg.obj.toString(),1);
                    break;
            }

        }
    };


}
