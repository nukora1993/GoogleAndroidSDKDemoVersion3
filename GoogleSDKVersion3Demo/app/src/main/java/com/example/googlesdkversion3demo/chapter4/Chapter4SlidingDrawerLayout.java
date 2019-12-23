package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter4SlidingDrawerLayout extends AppCompatActivity {
    private GridView gv;
    private SlidingDrawer sd;
    private ImageView im;
    private int[] icons={R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground};
    private String[] items={"background","foreground"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_sliding_drawer_layout);

        gv=findViewById(R.id.my_content);
        sd=findViewById(R.id.drawer);
        im=findViewById(R.id.image_view);

        MyGridViewAdapter adapter=new MyGridViewAdapter(this,items,icons);
        gv.setAdapter(adapter);

        sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                im.setImageResource(R.drawable.ic_launcher_background);
            }
        });

        sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                im.setImageResource(R.drawable.ic_launcher_foreground);
            }
        });
    }





    class MyGridViewAdapter extends BaseAdapter {
        private Context context;
        private String[] items;
        private int[] icons;

        public MyGridViewAdapter(Context context,String[] items,int[] icons){
            this.context=context;
            this.items=items;
            this.icons=icons;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater factory=LayoutInflater.from(context);
            View v=factory.inflate(R.layout.grid,null);
            ImageView iv=v.findViewById(R.id.icon);
            TextView tv=v.findViewById(R.id.text);
            iv.setImageResource(icons[position]);
            tv.setText(items[position]);
            return v;
        }
    }
}
