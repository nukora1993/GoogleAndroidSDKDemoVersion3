package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Chapter5FileExplorer extends ListActivity {
    private static final String TAG="Chapter5Log";
    //文件名和文件路径
    private List<String> items;
    private List<String> paths;
    private String rootPath= Environment.getExternalStorageDirectory().getAbsolutePath();
    private TextView path;
    private View myView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_file_explorer);
        //getDir会在/data/data/PackageName/下获取一个app_xxx的文件夹，不存在则创建
//        getDir();
        path=findViewById(R.id.path);
        getFileDir(rootPath);

    }

    private void getFileDir(String filePath){
        path.setText(filePath);
        items=new ArrayList<String>();
        paths=new ArrayList<String>();

        File f=new File(filePath);
        File[] files=f.listFiles();

        if(!filePath.equals(rootPath)){
            //回到根目录
            items.add("b1");
            paths.add(rootPath);
            //回上层
            items.add("b2");
            paths.add(f.getParent());
        }

        for (int i = 0; i <files.length ; i++) {
            File file=files[i];
            items.add(file.getName());
            paths.add(file.getPath());
        }

        setListAdapter(new MyAdapter(this,items,paths));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file=new File(paths.get(position));
        if(file.canRead()){
            //时文件夹则显示文件夹，不是则跳出选项
            if(file.isDirectory()){
                getFileDir(paths.get(position));
            }else{
                fileHandle(file);
            }
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Message")
                    .setMessage("Permission denied")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }

    private void fileHandle(final File file){
        DialogInterface.OnClickListener listener1=new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    openFile(file);
                    //更改文件名称
                }else if(which==1){
                    LayoutInflater inflater=LayoutInflater.from(getBaseContext());
                    //显示更改文件名称的dialog
                    myView=inflater.inflate(R.layout.rename_alert_dialog,null);
                    //在这个dialog里面有一个编辑框
                    editText=myView.findViewById(R.id.edit_text);

                    DialogInterface.OnClickListener listener2=new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //获得修改后的文件名称
                            String modName=editText.getText().toString();
                            //获取父路径
                            final String pFile=file.getParentFile().getPath()+"/";
                            final String newPath=pFile+modName;
                            //若文件已存在，提示是否覆盖
                            if(new File(newPath).exists()){
                                if(!modName.equals(file.getName())){
                                    new AlertDialog.Builder(getBaseContext())
                                            .setTitle("Notice")
                                            .setMessage("File Already Exists,Overwrite?")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    file.renameTo(new File(newPath));
                                                    getFileDir(pFile);
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).show();
                                }
                            }else{
                                file.renameTo(new File(newPath));
                                getFileDir(pFile);
                            }
                        }
                    };

                    AlertDialog renameDialog=new AlertDialog.Builder(Chapter5FileExplorer.this).create();
                    renameDialog.setView(myView);
                    renameDialog.setButton("OK",listener2);
                    renameDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    renameDialog.show();
                }else{
                    //删除文件,注意AlertDialog的context必须时创建它的Activity的this，不能时随便的Context，不然会失败
                    new AlertDialog.Builder(Chapter5FileExplorer.this)
                            .setTitle("Delete")
                            .setMessage("Are you sure?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    file.delete();
                                    getFileDir(file.getParent());
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            }
        };



        //选择文件时，跳出如何处理的listdialog
        String[] menu={"Open","Rename","Delete"};
        new AlertDialog.Builder(this)
                .setTitle("Choose")
                .setItems(menu,listener1)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();



    }

    private void openFile(File file){

    }

    class MyAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private Bitmap icon1;
        private Bitmap icon2;
        private Bitmap icon3;
        private Bitmap icon4;
        private List<String> items;
        private List<String> paths;

        public MyAdapter(Context context,List<String> items,List<String> paths){
            inflater=LayoutInflater.from(context);
            this.items=items;
            this.paths=paths;
            icon1= BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_dialog_info);
            icon2= BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_dialog_info);
            icon3= BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_dialog_info);
            icon4= BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_dialog_info);



        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if(convertView==null){
                convertView=inflater.inflate(R.layout.file_row_with_icon,null);
                holder=new ViewHolder();
                holder.textView=convertView.findViewById(R.id.text_view);
                holder.icon=convertView.findViewById(R.id.image_view);

                convertView.setTag(holder);
            }else{
                holder=(ViewHolder)convertView.getTag();
            }

            File f=new File(paths.get(position));
            if(items.get(position).equals("b1")){
                holder.textView.setText("Back to root");
                holder.icon.setImageBitmap(icon1);
            }else if(items.get(position).equals("b2")){
                holder.textView.setText("Back to ..");
                holder.icon.setImageBitmap(icon2);
            }else{
                holder.textView.setText(f.getName());
                if(f.isDirectory()){
                    holder.icon.setImageBitmap(icon3);
                }else{
                    holder.icon.setImageBitmap(icon4);
                }
            }
            return convertView;
        }

        class ViewHolder{
            TextView textView;
            ImageView icon;
        }
    }
}
