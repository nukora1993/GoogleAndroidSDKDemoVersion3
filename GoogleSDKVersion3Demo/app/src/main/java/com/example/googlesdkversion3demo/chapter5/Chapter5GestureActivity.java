package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

public class Chapter5GestureActivity extends AppCompatActivity {
    private Gesture gesture;
    private GestureLibrary library;
    private GestureOverlayView overlayView;
    private Button button1,button2,button3;
    private EditText editText;
    private String gesPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_gesture);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            }
        }

        editText=findViewById(R.id.edit_text);
        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);
        button3=findViewById(R.id.button_3);
        overlayView=findViewById(R.id.gesture_overlay_view);

        gesPath= new File(Environment.getExternalStorageDirectory(),"gestures").getAbsolutePath();
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(gesture!=null&&editText.getText().length()!=0){
                    button1.setEnabled(true);
                }else{
                    button1.setEnabled(false);
                }
                return false;
            }
        });

        overlayView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            //开始时，先清除之前的gesture
            @Override
            public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
                button1.setEnabled(false);
                gesture=null;
            }


            @Override
            public void onGesture(GestureOverlayView overlay, MotionEvent event) {

            }

            @Override
            public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
                gesture=overlayView.getGesture();
                if(gesture!=null&&editText.getText().length()!=0){
                    button1.setEnabled(true);
                }
            }

            @Override
            public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gesName=editText.getText().toString();
                try{
                    File file=new File(gesPath);
                    library= GestureLibraries.fromFile(gesPath);
                    if(!file.exists()){
                        library.addGesture(gesName,gesture);
                        if(library.save()){
                            editText.setText("");
                            button1.setEnabled(false);
                            overlayView.clear(true);
                            Toast.makeText(Chapter5GestureActivity.this,"gesture save success!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Chapter5GestureActivity.this,"gesture save failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    //若文件已经存在，则尝试读取
                    else{
                        if(!library.load()){
                            Toast.makeText(Chapter5GestureActivity.this,"load gesture failed!",Toast.LENGTH_SHORT).show();
                        }else{
                            //成功读取，但是要判断是否存在名称相同的gesture，如果存在，先移除掉(不知道能不能直接覆盖）
                            Set<String> en=library.getGestureEntries();
                            if(en.contains(gesName)){
                                ArrayList<Gesture> al= library.getGestures(gesName);
                                for (int i = 0; i <al.size() ; i++) {
                                    //这个逻辑表示，同一个gestureName可以对应多个gesture？
                                    library.removeGesture(gesName,al.get(i));
                                }
                            }
                            library.addGesture(gesName,gesture);
                            if(library.save()){
                                editText.setText("");
                                button1.setEnabled(false);
                                overlayView.clear(true);
                                Toast.makeText(Chapter5GestureActivity.this,"gesture save success!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Chapter5GestureActivity.this,"gesture save failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }


            }


        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                button1.setEnabled(false);
                overlayView.clear(true);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Object[] en=library.getGestureEntries().toArray();
                    for (int i = 0; i <en.length ; i++) {
                        ArrayList<Gesture> al=library.getGestures(en[i].toString());
                        for (int j = 0; j <al.size() ; j++) {
                            Gesture gs=(Gesture)al.get(j);
                            //可以将Gesture转换为bitmap显示
                            Bitmap bmap=gs.toBitmap(64,64,12, Color.GREEN);
                            ImageView imageView=new ImageView(Chapter5GestureActivity.this);
                            imageView.setImageBitmap(bmap);
                            setContentView(imageView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
