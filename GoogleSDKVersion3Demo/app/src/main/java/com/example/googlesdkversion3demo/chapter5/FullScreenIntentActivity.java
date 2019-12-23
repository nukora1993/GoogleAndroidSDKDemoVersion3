package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class FullScreenIntentActivity extends AppCompatActivity {
    private static final String TAG="Chapter5Log";
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_intent);
        textView=findViewById(R.id.text_view);

        try{
            Bundle bundle=getIntent().getExtras();
            String strNotificationId=bundle.getString("MY_NOTIFICATION_ID");

            textView.setText("Downloading "+strNotificationId);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
