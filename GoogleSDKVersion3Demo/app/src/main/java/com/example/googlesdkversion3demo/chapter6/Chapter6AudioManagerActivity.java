package com.example.googlesdkversion3demo.chapter6;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

public class Chapter6AudioManagerActivity extends AppCompatActivity {
    private static final String TAG="Chapter6Log";
    private TextView textView1,textView2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter6_audio_manager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG},0);
            }
        }

        MyPhoneCallListener myPhoneCallListener=new MyPhoneCallListener();

        TelephonyManager telephonyManager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);

        telephonyManager.listen(myPhoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);



        textView1=findViewById(R.id.text_view_1);
        textView2=findViewById(R.id.text_view_2);

    }

    class MyPhoneCallListener extends PhoneStateListener{
        //注意在新版本中，要获得手机号必须要加上权限android.permission.READ_CALL_LOG
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    textView1.setText("IDLE");
                    try{
                        AudioManager audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
                        if(audioManager!=null){
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            audioManager.getStreamVolume(AudioManager.STREAM_RING);
                        }
                    }catch(Exception e){
                        textView1.setText(e.toString());
                        e.printStackTrace();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    textView1.setText("OFF_HOOK");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d(TAG,"bug:"+phoneNumber);
                    Log.d(TAG,"RING...");
                    textView1.setText(phoneNumber);
                    if(phoneNumber.equals(textView2.getText().toString())){
                        try{
                            AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                            if(audioManager!=null){
                                Log.d(TAG,"here");
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                audioManager.getStreamVolume(AudioManager.STREAM_RING);
                                Toast.makeText(Chapter6AudioManagerActivity.this,
                                        "静音模式",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            textView1.setText(e.toString());
                            e.printStackTrace();
                        }
                    }
            }
        }


    }
}
