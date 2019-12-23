package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.lang.reflect.Array;

public class Chapter4ListViewActivity extends AppCompatActivity {
    private static final String[] array={
            "aa","bb","cc","dd"
    };

    LinearLayout mLinearLayout;
    TextView mTextView;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chapter4_list_view);

        mLinearLayout=new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setBackgroundColor(Color.WHITE);

        mTextView=new TextView(this);
        LinearLayout.LayoutParams param1=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        mTextView.setText("title");
        mTextView.setTextColor(Color.BLUE);


        mLinearLayout.addView(mTextView,param1);

        mListView=new ListView(this);
        LinearLayout.LayoutParams param2=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        mListView.setBackgroundColor(Color.GRAY);
        mLinearLayout.addView(mListView,param2);

        //动态加载
        setContentView(mLinearLayout);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.my_simple_list_item,array);

        mListView.setAdapter(adapter);

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTextView.setText("You choose"+parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTextView.setText("You choose"+array[position]);
            }
        });


    }
}
