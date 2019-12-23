package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter7CustomButtonActivity extends AppCompatActivity {
    private TextView textView;
    private CustomButton customButton1,customButton2;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_custom_button);

        textView=findViewById(R.id.text_view);

        linearLayout=findViewById(R.id.linear_layout);

        //customButton2是xml配置，不配置动画效果
        customButton2=findViewById(R.id.custom_button_2);

        customButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("button2 clicked");
            }
        });


        linearLayout=findViewById(R.id.linear_layout);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        customButton1=new CustomButton(this);
        customButton1.setText("This is custom button1");

        linearLayout.addView(customButton1,layoutParams);

        customButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("button1 clicked");
            }
        });





    }
}
