package com.example.googlesdkversion3demo.chapter6;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter6SensorActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private TextView textView;
    private int ringerMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter6_sensor);

        textView=findViewById(R.id.text_view);
        try{
            sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        }catch (Exception e){
            e.printStackTrace();
        }

        getAudioManagerMode();
        switch(ringerMode){
            case AudioManager.RINGER_MODE_NORMAL:
                textView.setText("normal mode");
                break;
            case AudioManager.RINGER_MODE_SILENT:
                textView.setText("silent");
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                textView.setText("vibrate");
                break;
        }
    }

    private final SensorEventListener sensorEventListener=
            new SensorEventListener() {
                private float x,y,z;
                @Override
                public void onSensorChanged(SensorEvent event) {
                    synchronized (sensorManager){
                        switch(event.sensor.getType()){
                            case Sensor.TYPE_ACCELEROMETER:
                                x=event.values[0];
                                y=event.values[1];
                                z=event.values[2];
                                if(z>9){
                                    changeToNormalMode();
                                    switch (ringerMode){
                                        case AudioManager.RINGER_MODE_NORMAL:
                                            textView.setText("normal mode");
                                            break;
                                        case AudioManager.RINGER_MODE_SILENT:
                                            textView.setText("silent");
                                            break;
                                        case AudioManager.RINGER_MODE_VIBRATE:
                                            textView.setText("vibrate");
                                            break;
                                    }
                                }else if(z<-9){
                                    changeToSilentMode();
                                    changeToVibrateMode();
                                    switch (ringerMode){
                                        case AudioManager.RINGER_MODE_NORMAL:
                                            textView.setText("normal mode");
                                            break;
                                        case AudioManager.RINGER_MODE_SILENT:
                                            textView.setText("silent");
                                            break;
                                        case AudioManager.RINGER_MODE_VIBRATE:
                                            textView.setText("vibrate");
                                            break;
                                    }
                                }

                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };

    private void getAudioManagerMode(){
        try{
            AudioManager audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
            if(audioManager!=null){
                ringerMode=audioManager.getRingerMode();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void changeToSilentMode(){
        try{
            AudioManager audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
            if(audioManager!=null){
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                ringerMode=audioManager.getRingerMode();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void changeToVibrateMode(){
        try{
            AudioManager audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
            if(audioManager!=null){
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                ringerMode=audioManager.getRingerMode();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void changeToNormalMode(){
        try{
            AudioManager audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
            if(audioManager!=null){
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                ringerMode=audioManager.getRingerMode();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }
}
