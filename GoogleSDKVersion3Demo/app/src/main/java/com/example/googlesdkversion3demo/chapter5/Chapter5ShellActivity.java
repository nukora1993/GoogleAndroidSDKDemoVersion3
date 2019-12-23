package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.io.DataInputStream;

public class Chapter5ShellActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_shell);

        button=findViewById(R.id.button);
        editText=findViewById(R.id.edit_text);
        textView=findViewById(R.id.text_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editText.getText().toString())){
                    runRootCommand(editText.getText().toString());
                    editText.setText("");
                }
            }
        });
    }

    private void runRootCommand(String command){
        Process process=null;
        try{
            //不知道为什么，权限很低，ls都执行不了
            process=Runtime.getRuntime().exec(command);
            StringBuffer output=new StringBuffer();

            DataInputStream stdout=new DataInputStream(process.getInputStream());
            int c=0;
            while((c=stdout.read())!=-1){
                output.append((char)c);
            }
            process.waitFor();
            textView.setText(output.toString());
        }catch (Exception e){
            textView.setText(e.getMessage());
        }finally {
            try{
                process.destroy();
            }catch (Exception e){
                textView.setText(e.getMessage());
            }
        }
    }
}
