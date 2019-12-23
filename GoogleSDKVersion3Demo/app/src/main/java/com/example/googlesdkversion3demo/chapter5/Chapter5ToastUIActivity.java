package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

public class Chapter5ToastUIActivity extends AppCompatActivity {
    private ImageView imageView1;
    private ImageView imageView2;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_toast_ui);
        imageView1=findViewById(R.id.image_view_1);
        imageView2=findViewById(R.id.image_view_2);

        button=findViewById(R.id.button);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Chapter5ToastUIActivity.this)
                        .setMessage("Message")
                        .setNeutralButton("OK",null)
                        .show();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Chapter5ToastUIActivity.this)
                        .setMessage("Message2")
                        .setNeutralButton("OK",null)
                        .show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HackerView hView=(HackerView)getLayoutInflater().inflate(R.layout.hacker_layout,null);
                hView.setActivityToSpoof(Chapter5ToastUIActivity.this);
                Toast toast=new Toast(getApplicationContext());
                toast.setGravity(Gravity.FILL,0,0);
                toast.setView(hView);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
