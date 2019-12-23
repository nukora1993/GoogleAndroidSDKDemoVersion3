package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.googlesdkversion3demo.R;

public class Chapter7AudioVolumeActivity extends AppCompatActivity {
    private static final String TAG="Chapter7Log";
    private ImageView imageView;
    private ImageButton downButton,upButton,normalButton,muteButton,vibrateButton;
    private ProgressBar progressBar;
    private AudioManager audioManager;
    private int volume=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if(checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS)!= PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},0);
//            }
//        }
        //非常坑爹，允许勿扰模式下更改音量需要进入系统设置
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            if(!notificationManager.isNotificationPolicyAccessGranted()){
                Intent i=new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(i);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_audio_volume);
        audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
        imageView=findViewById(R.id.image_view);
        progressBar=findViewById(R.id.progress_bar);

        progressBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));


        downButton=findViewById(R.id.down);
        upButton=findViewById(R.id.up);
        normalButton=findViewById(R.id.normal);
        muteButton=findViewById(R.id.mute);
        vibrateButton=findViewById(R.id.vibrate);

        volume=audioManager.getStreamVolume(AudioManager.STREAM_RING);
        progressBar.setProgress(volume);

        int mode=audioManager.getRingerMode();

        if(mode==AudioManager.RINGER_MODE_NORMAL){
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_away));

        }else if(mode==AudioManager.RINGER_MODE_SILENT){
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_busy));
        }else if(mode==AudioManager.RINGER_MODE_VIBRATE){
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_online));
        }

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                audioManager.adjustStreamVolume(AudioManager.STREAM_RING,AudioManager.ADJUST_LOWER,0);
                volume=audioManager.getStreamVolume(AudioManager.STREAM_RING);

                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM)+"system");
                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)+"music");
                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)+"noti");
                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL)+"voice");
                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_ALARM)+"alarm");
                Log.d(TAG,"volume:"+volume);
                progressBar.setProgress(volume);
                int mode=audioManager.getRingerMode();
                if(mode==AudioManager.RINGER_MODE_NORMAL){
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_away));

                }else if(mode==AudioManager.RINGER_MODE_SILENT){
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_busy));
                }else if(mode==AudioManager.RINGER_MODE_VIBRATE){
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_online));
                }
            }
        });

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_RING,AudioManager.ADJUST_RAISE,0);
                volume=audioManager.getStreamVolume(AudioManager.STREAM_RING);

                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM)+"system");
                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)+"music");
                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)+"noti");
                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL)+"voice");
                Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_ALARM)+"alarm");
                Log.d(TAG,"volume:"+volume);
                progressBar.setProgress(volume);
                int mode=audioManager.getRingerMode();
                if(mode==AudioManager.RINGER_MODE_NORMAL){
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_away));

                }else if(mode==AudioManager.RINGER_MODE_SILENT){
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_busy));
                }else if(mode==AudioManager.RINGER_MODE_VIBRATE){
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_audio_online));
                }
            }
        });


    }
}
