package com.example.googlesdkversion3demo.chapter4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.util.ArrayList;
import java.util.List;

public class Chapter4SpinnerActivity extends AppCompatActivity {
    private static final String TAG="Chapter4Log";

    private static final String[] countriesStr={"beijing","shanghai","tianjin"};
    private TextView myTextView;
    //下拉列表
    private Spinner mySpinner;
    private ArrayAdapter<String> adapter;

    private EditText myEditText;
    private Button myButtonAdd;
    private Button myButtonRemove;

    private List<String> allCountries;
    Animation myAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_spinner);

        allCountries=new ArrayList<String>();
        for (int i = 0; i <countriesStr.length ; i++) {
            allCountries.add(countriesStr[i]);
        }





        myTextView=findViewById(R.id.myTextView);
        mySpinner=findViewById(R.id.mySpinner);
        myEditText=findViewById(R.id.myEditText);
        myButtonAdd=findViewById(R.id.myButtonAdd);
        myButtonRemove=findViewById(R.id.myButtonRemove);

        myButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //很奇怪,这里不需要notify adapter来刷新
                String newCountry=myEditText.getText().toString();
                adapter.add(newCountry);
                int position=adapter.getPosition(newCountry);
                mySpinner.setSelection(position);
                myEditText.setText("");
            }
        });
        

        myButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(mySpinner.getSelectedItem().toString());
                myEditText.setText("");

            }
        });


//        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
//                countriesStr);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                allCountries);

        adapter.setDropDownViewResource(R.layout.myspinner_dropdown);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myTextView.setText("You choose:"+allCountries.get(position));
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        mySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//        });

        myAnimation= AnimationUtils.loadAnimation(this,R.anim.my_anim);

        mySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.startAnimation(myAnimation);
                v.setVisibility(View.INVISIBLE);
                return false;
            }
        });





    }
}
