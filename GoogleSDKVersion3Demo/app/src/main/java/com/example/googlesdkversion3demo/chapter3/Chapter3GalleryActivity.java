package com.example.googlesdkversion3demo.chapter3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

import java.util.Locale;


public class Chapter3GalleryActivity extends AppCompatActivity {
    private static final String TAG="Chapter3Log";
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter3_gallery);

        ((Gallery)findViewById(R.id.my_gallery)).setAdapter(new ImageAdapter(this));

//        new AlertDialog.Builder(this)
//                .setTitle("alert")
//                .setItems(new CharSequence[]{"option1", "option2"}, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //AlertDialog可嵌套
//                        String[] arrShop=new String[]{"option1","option2"};
//                        new AlertDialog.Builder(Chapter3GalleryActivity.this)
//                                .setMessage("message")
//                                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(Chapter3GalleryActivity.this,"You clicked ok",Toast.LENGTH_LONG).show();
//                                    }
//                                }).show();
//                    }
//                }).show();

        ((TextView)findViewById(R.id.edit_text1)).setTransformationMethod(
//                HideReturnsTransformationMethod.getInstance()
                //设置显示为密码
                PasswordTransformationMethod.getInstance()
        );

        //切换locale,实测高版本无效果
//        Resources res=getResources();
//        Configuration conf=res.getConfiguration();
//        conf.locale= Locale.ENGLISH;
//        DisplayMetrics dm=res.getDisplayMetrics();
//        res.updateConfiguration(conf,dm);

        //获得targetSDK版本（注意不是用户手机的版本）
        Log.d(TAG,getApplicationInfo().targetSdkVersion+"");




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"about");
        menu.add(0,1,1,"exit");
        return super.onCreateOptionsMenu(menu);
    }

    public class ImageAdapter extends BaseAdapter{
        private Context myContext;

        private int[] myImageIds={android.R.drawable.btn_dialog,
                android.R.drawable.btn_radio,
                android.R.drawable.ic_lock_idle_alarm,
                android.R.drawable.ic_lock_idle_alarm,
                android.R.drawable.ic_lock_idle_alarm,
                android.R.drawable.ic_lock_idle_alarm,
                android.R.drawable.ic_lock_idle_alarm};

        public ImageAdapter(Context context){
            myContext=context;
        }

        @Override
        public int getCount() {
            return myImageIds.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i=new ImageView(myContext);
            i.setImageResource(myImageIds[position]);
            i.setScaleType(ImageView.ScaleType.FIT_XY);
            i.setLayoutParams(new Gallery.LayoutParams(200,200));
            return i;
        }

        public float getScale(boolean focused,int offset){
            return Math.max(0,1.0f/(float)Math.pow(2,Math.abs(offset)));
        }

    }
}
