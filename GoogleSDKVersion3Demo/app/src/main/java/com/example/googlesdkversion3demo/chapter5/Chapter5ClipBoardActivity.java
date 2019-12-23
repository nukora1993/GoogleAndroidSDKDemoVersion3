package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

import org.w3c.dom.Text;

public class Chapter5ClipBoardActivity extends AppCompatActivity {
    private MyEditText myEditText;
    private TextView textView;
    private Button button1,button2;
    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_clip_board);
        myEditText=findViewById(R.id.my_edit_text);
        textView=findViewById(R.id.text_view);
        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);

        clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myEditText.getSelectedText().length()==0){
                    Toast.makeText(Chapter5ClipBoardActivity.this,"未选取文字",Toast.LENGTH_LONG).show();

                }else{
                    clipboardManager.setText(myEditText.getSelectedText());
                    Toast.makeText(Chapter5ClipBoardActivity.this,"已复制",Toast.LENGTH_LONG).show();

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(clipboardManager.getText());
                Toast.makeText(Chapter5ClipBoardActivity.this,"已粘贴",Toast.LENGTH_LONG).show();
            }
        });
    }
}
