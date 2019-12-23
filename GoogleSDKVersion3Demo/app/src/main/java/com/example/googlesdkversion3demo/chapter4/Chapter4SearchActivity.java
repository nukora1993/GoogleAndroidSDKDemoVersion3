package com.example.googlesdkversion3demo.chapter4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Chapter4SearchActivity extends AppCompatActivity {
    private static final int MENU_SEARCH=1;
//    private android.widget.Gallery gallery;
    private TextView textView;
    private ArrayList<String> fileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_search);

//        gallery=findViewById(R.id.gallery);
        textView=findViewById(R.id.text_view);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        Intent queryIntent=getIntent();

        final String queryAction=queryIntent.getAction();
        if(Intent.ACTION_SEARCH.equals(queryAction)){
            String query=queryIntent.getStringExtra(SearchManager.QUERY);
            queryPicture(query);
        }

        queryPicture("2019-12-17");
        //在前activity中打开拨号器、执行快捷键、启动本地搜索、启动全局搜索
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,MENU_SEARCH,0,"Search").setIcon(android.R.drawable.ic_search_category_default)
                .setAlphabeticShortcut(SearchManager.MENU_KEY);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case MENU_SEARCH:
                onSearchRequested();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void queryPicture(String strQuery){
        fileNames=new ArrayList<String>();
        String[] projection={
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.DISPLAY_NAME
        };

        //搜索条件
        String selection="("+projection[1]+">=? or "+
                projection[1]+"<=? )";

        String selectionArgs[]=getStartEnd(strQuery);

        //关键方法，调用managedQuery，底层用binder实现，向系统请求搜索服务
        Cursor cursor=managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,selection,selectionArgs,null
        );

        while(cursor.moveToNext()){
            String fileName=cursor.getString(0);
            fileNames.add(fileName);
        }

        textView.setText(strQuery+"共有"+fileNames.size()+ Arrays.deepToString(fileNames.toArray()));


    }

    private String[] getStartEnd(String strQueryDate){
        String[] result={"",""};
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        try{
            result[0]=sd.format(sd.parse(strQueryDate));
            result[1]=sd.format(sd.parse(strQueryDate));
        }catch (Exception e){
            e.printStackTrace();
        }


        return result;
    }
}
