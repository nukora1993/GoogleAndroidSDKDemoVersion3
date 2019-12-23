package com.example.googlesdkversion3demo.chapter4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Chapter4FileExplorerListActivity extends ListActivity {
    private List<String> items=null;
    private List<String> paths=null;
    //由于权限的问题，不能list /目录
    private String rootPath= Environment.getExternalStorageDirectory().getAbsolutePath();
    private TextView mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listActivity也可以加载view，只不过layout中必须包含指定id的listview
        setContentView(R.layout.activity_chapter4_file_explorer_list);
        mPath=findViewById(R.id.path);

        //列目录需要读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            }else{
                getFileDir(rootPath);
            }
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        getFileDir(rootPath);
    }

    private void getFileDir(String filePath){
        mPath.setText(filePath);
        items=new ArrayList<String>();
        paths=new ArrayList<String>();
        File f=new File(filePath);
        File[] files=f.listFiles();

        if(!filePath.equals(rootPath)){
            items.add("Back to "+rootPath);
            paths.add(rootPath);
            items.add("Back to ../");
            paths.add(f.getParent());
        }

        for (int i = 0; i <files.length ; i++) {
            File file=files[i];
            items.add(file.getName());
            paths.add(file.getPath());
        }

        ArrayAdapter<String> fileList=new ArrayAdapter<String>(this,R.layout.file_row,items);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file=new File(paths.get(position));
        if(file.canRead()){
            if(file.isDirectory()){
                getFileDir(paths.get(position));
            }
            else{
                new AlertDialog.Builder(this)
                        .setTitle("Message")
                        .setMessage("["+file.getName()+"]"+" is File!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Message")
                    .setMessage("Permission denied!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }
}
