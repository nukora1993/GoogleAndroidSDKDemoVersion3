package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter5LinkifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_linkfy);

        final TextView mTextView=findViewById(R.id.text_view);
        final EditText mEditText=findViewById(R.id.edit_text);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTextView.setText(mEditText.getText());

                //给textview自动加上链接，当然也可以设置textview的autolink属性为true
                Linkify.addLinks(mTextView,Linkify.WEB_URLS|
                        Linkify.EMAIL_ADDRESSES|
                        Linkify.PHONE_NUMBERS);

                
            }


        });
        //实测虚拟键盘输入无法触发onKeyListener
//        mEditText.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                return true;
//            }
//        });

    }
}
