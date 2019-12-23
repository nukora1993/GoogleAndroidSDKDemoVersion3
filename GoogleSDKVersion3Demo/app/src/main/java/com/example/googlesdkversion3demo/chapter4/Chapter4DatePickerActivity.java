package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.googlesdkversion3demo.R;

import java.util.Calendar;

public class Chapter4DatePickerActivity extends AppCompatActivity {
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    TextView tv;
    TimePicker tp;
    DatePicker dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_date_picker);

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        mHour=c.get(Calendar.HOUR_OF_DAY);
        mMinute=c.get(Calendar.MINUTE);

        tv=findViewById(R.id.showTime);
        updateDisplay();

        dp=findViewById(R.id.dPicker);
        dp.init(mYear,mMonth,mDay,new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear=year;
                mMonth=monthOfYear;
                mDay=dayOfMonth;

                updateDisplay();
            }
        });

        tp=findViewById(R.id.tPicker);
        tp.setIs24HourView(true);
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour=hourOfDay;
                mMinute=minute;
                updateDisplay();
            }
        });



    }



    private void updateDisplay(){
        tv.setText(new StringBuilder().append(mYear)
                .append("/")
        .append(format(mMonth+1))
        .append("/")
        .append(format(mDay))
        .append(" ")
        .append(format(mHour))
        .append(":")
        .append(format(mMinute)));
    }

    private String format(int x){
        String s=""+x;
        if(s.length()==1){
            s="0"+s;
        }
        return s;
    }
}
