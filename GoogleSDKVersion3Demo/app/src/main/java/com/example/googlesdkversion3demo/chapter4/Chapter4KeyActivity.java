package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.googlesdkversion3demo.MainActivity;
import com.example.googlesdkversion3demo.R;

public class Chapter4KeyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_key);

        final EditText editText1=(EditText)findViewById(R.id.chapter4_edit_text_1);
        final TextView textView1=(TextView)findViewById(R.id.chapter4_text_view_1);
        editText1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                textView1.setText(
                        editText1.getText()
                );
                return false;
            }
        });
    }
}
