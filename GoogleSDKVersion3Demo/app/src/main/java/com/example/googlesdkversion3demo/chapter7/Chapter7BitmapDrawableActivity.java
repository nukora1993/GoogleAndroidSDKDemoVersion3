package com.example.googlesdkversion3demo.chapter7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter7BitmapDrawableActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;

    private static final int CONTEXT_ITEM1= Menu.FIRST;
    private static final int CONTEXT_ITEM2=Menu.FIRST+1;
    private static final int CONTEXT_ITEM3=Menu.FIRST+2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_bitmap_drawable);
        textView=findViewById(R.id.text_view);
        imageView=findViewById(R.id.image_view);

        imageView.setOnCreateContextMenuListener(
                new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.add(Menu.NONE,CONTEXT_ITEM1,0,"context1");
                        menu.add(Menu.NONE,CONTEXT_ITEM2,0,"context2");
                        menu.add(Menu.NONE,CONTEXT_ITEM3,0,"context3");
                    }
                }
        );
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.blog);

        int height=bitmap.getHeight();
        int width=bitmap.getWidth();

        try{
            switch(item.getItemId()){
                case CONTEXT_ITEM1:
                    String opt="width:"+width;
                    textView.setText(opt);
                    break;
                case CONTEXT_ITEM2:
                    String opt2="height:"+height;
                    textView.setText(opt2);
                    break;
                case CONTEXT_ITEM3:
                    String opt3="width:"+width+","+"height:"+height;
                    textView.setText(opt3);
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.onContextItemSelected(item);
    }
}
