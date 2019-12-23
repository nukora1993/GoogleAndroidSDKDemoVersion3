package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.util.List;

public class Chapter7VoiceRecogActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_voic_recog);

        textView=findViewById(R.id.text_view);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager=getPackageManager();
                //实测S10国行版没有该项服务
                List<ResolveInfo> activities=packageManager.queryIntentActivities(
                        new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0
                );

                if(activities.size()!=0){
                    try{
                        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"语音识别");
                        startActivityForResult(intent,0);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    textView.setText("no engine");
                }
            }
        });
    }
}
