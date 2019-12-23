package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.io.File;
import java.io.IOException;

public class Chapter7RecorderActivity extends AppCompatActivity {
    private Button button1,button2,button3,button4;
    private File recAudioFile;
    private File recAudioDir;
    private File playFile;
    private MediaRecorder mediaRecorder;
    private TextView textView;
    private boolean isStopRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},0);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_recorder);

        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);
        button3=findViewById(R.id.button_3);
        button4=findViewById(R.id.button_4);

        textView=findViewById(R.id.text_view);

        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);

        recAudioDir= Environment.getExternalStorageDirectory();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    recAudioFile=File.createTempFile("temp",".amr",recAudioDir);
                    playFile=recAudioFile;
                    mediaRecorder=new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                    mediaRecorder.setOutputFile(recAudioFile.getAbsolutePath());
                    //非常坑爹，Encoder需要在最后设置，否则报illegalState
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    textView.setText("录音中");
                    button2.setEnabled(true);
                    button3.setEnabled(false);
                    button4.setEnabled(false);
                    isStopRecord=false;
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder=null;
                textView.setText("停止"+recAudioFile.getAbsolutePath());
                button2.setEnabled(false);
                button3.setEnabled(true);
                button4.setEnabled(true);
                isStopRecord=true;

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playFile!=null&&playFile.exists()){
                    openFile(playFile);
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playFile!=null){
                    playFile.delete();
                    textView.setText("完成删除");
                }
            }
        });



    }

    @Override
    protected void onStop() {
        if(mediaRecorder!=null&&!isStopRecord){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
        }
        super.onStop();
    }

    private void openFile(File f){
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        //由于高版本不允许直接暴露文件绝对路径，应该使用fileprovider，这里就不再测试了
        intent.setDataAndType(Uri.fromFile(f),"audio/*");
//        startActivity(intent);
    }
}
