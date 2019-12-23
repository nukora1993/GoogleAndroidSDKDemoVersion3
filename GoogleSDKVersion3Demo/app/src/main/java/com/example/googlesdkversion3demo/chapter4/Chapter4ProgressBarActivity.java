package com.example.googlesdkversion3demo.chapter4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter4ProgressBarActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    private ProgressBar mProgressBar;
    public int intCounter=0;

    protected static final int GUI_STOP_NOTIFIER=0x108;
    protected static final int GUI_THREADING_NOTIFIER=0x109;

    //可以使用setProgressBar来更新进度
    Handler myMessageHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case GUI_STOP_NOTIFIER:
                    mTextView.setText("done");
                    mProgressBar.setVisibility(View.GONE);
                    Thread.currentThread().interrupt();
                    break;
                case GUI_THREADING_NOTIFIER:
                    if(!Thread.currentThread().isInterrupted()){
                        mProgressBar.setProgress(intCounter);
                        mTextView.setText("Progress:"+intCounter);
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_progress_bar);

        mButton=findViewById(R.id.myButton);
        mTextView=findViewById(R.id.myTextView);

        mProgressBar=findViewById(R.id.myProgressBar);
        //false表示不确定进度
        mProgressBar.setIndeterminate(false);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("start");
                mProgressBar.setMax(100);
                mProgressBar.setProgress(0);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <10 ; i++) {
                            try{
                                intCounter=(i+1)*20;
                                Thread.sleep(1000);
                                if(i==4){
                                    Message m=new Message();
                                    m.what=GUI_STOP_NOTIFIER;
                                    myMessageHandler.sendMessage(m);
                                    break;
                                }else{
                                    Message m=new Message();
                                    m.what=GUI_THREADING_NOTIFIER;
                                    myMessageHandler.sendMessage(m);
                                }
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
}
