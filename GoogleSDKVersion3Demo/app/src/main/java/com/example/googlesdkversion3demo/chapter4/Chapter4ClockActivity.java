package com.example.googlesdkversion3demo.chapter4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AnalogClock;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.util.Calendar;

public class Chapter4ClockActivity extends AppCompatActivity {
    protected static final int GUINOTIFIER = 0x1234;

    private TextView mTextView;
    public AnalogClock mAnalogClock;


    public int mMinutes;
    public int mHour;

    public Handler mHandler;
    private Thread mClockThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_clock);

        mTextView = findViewById(R.id.myTextView);
        mAnalogClock = findViewById(R.id.myAnalogClock);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case GUINOTIFIER:
                        mTextView.setText(mHour + " : " + mMinutes);
                        break;
                }
                super.handleMessage(msg);
            }
        };

        mClockThread = new LooperThread();
        mClockThread.start();
    }


    class LooperThread extends Thread{
        @Override
        public void run() {
            try{
                do{
                    long time=System.currentTimeMillis();
                    final Calendar mCalendar=Calendar.getInstance();
                    mCalendar.setTimeInMillis(time);
                    mHour=mCalendar.get(Calendar.HOUR);
                    mMinutes=mCalendar.get(Calendar.MINUTE);

                    Thread.sleep(1000);
                    Message m=new Message();
                    m.what=GUINOTIFIER;
                    mHandler.sendMessage(m);
                }while(LooperThread.interrupted()==false);

            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
