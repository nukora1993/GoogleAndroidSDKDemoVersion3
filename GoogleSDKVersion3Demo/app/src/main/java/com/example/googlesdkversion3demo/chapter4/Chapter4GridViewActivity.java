package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter4GridViewActivity extends AppCompatActivity {
    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;
    private GridView mGridView1;
    private String[] mGames1,mGames2;
//    ArrayAdapter适用于构建比较简单的展示，传入的resId必须包含一个textView
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_grid_view);

        mGames1=new String[]{
              "aaaaaaa",
              "bbbbbbb",
              "ccccccc",
              "ddddddd"
        };

        mGames2=new String[]{
                "aaaaaaa",
                "bbbbbbb",
                "ccccccc",
                "ddddddd",
                "aaaaaaa",
                "bbbbbbb",
                "ccccccc",
                "ddddddd",
                "aaaaaaa"
        };

        mButton1=findViewById(R.id.button_1);
        mButton2=findViewById(R.id.button_2);
        mGridView1=findViewById(R.id.grid_view_1);
        mTextView1=findViewById(R.id.text_view_1);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //4个元素，以2列方式显示
                mGridView1.setNumColumns(2);
                arrayAdapter=new ArrayAdapter<String>(Chapter4GridViewActivity.this,
                        R.layout.simple_list_item_1_small,mGames1);

                mGridView1.setAdapter(arrayAdapter);
                mGridView1.setSelection(2);
                mGridView1.refreshDrawableState();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //9个元素，以3列显示
                mGridView1.setNumColumns(3);
                arrayAdapter=new ArrayAdapter<String>(Chapter4GridViewActivity.this,
                        R.layout.simple_list_item_1_small,mGames2);

                mGridView1.setAdapter(arrayAdapter);

            }
        });

        mGridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断adapter中元素数量，判断被单击的元素名称
                switch(arrayAdapter.getCount()){
                    case 4:
                        mTextView1.setText(mGames1[position]);
                        break;
                    case 9:
                        mTextView1.setText(mGames2[position]);
                        break;
                }
            }
        });
    }
}
