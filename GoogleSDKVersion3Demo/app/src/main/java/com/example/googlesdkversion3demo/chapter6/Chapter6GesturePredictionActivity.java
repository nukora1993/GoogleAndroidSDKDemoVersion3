package com.example.googlesdkversion3demo.chapter6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Chapter6GesturePredictionActivity extends AppCompatActivity {
    private static final String TAG="Chapter6";
    private GestureLibrary gestureLibrary;
    private GestureOverlayView gestureOverlayView;
    private String gesPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter6_gesture_prediction);

        gesPath=new File(Environment.getExternalStorageDirectory(),"gestures").getAbsolutePath();

        File file=new File(gesPath);
        Log.d(TAG,"file exists:"+file.exists());



        gestureLibrary= GestureLibraries.fromFile(gesPath);

        Log.d(TAG,"load:"+gestureLibrary.load());

        gestureOverlayView=findViewById(R.id.gesture_overlay_view);
        gestureOverlayView.addOnGesturePerformedListener(new MyListener(this));



    }

    class MyListener implements GestureOverlayView.OnGesturePerformedListener{
        private Context context;
        public MyListener(Context context){
            this.context=context;
        }

        @Override
        public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
            ArrayList<Prediction> predictions=gestureLibrary.recognize(gesture);
            Log.d(TAG,"prediction:"+ Arrays.deepToString(predictions.toArray()));
            Log.d(TAG,"size:"+predictions.size());
            if(predictions.size()>0){
                Prediction prediction=predictions.get(0);
                if(prediction.score>1.0){
                    Toast.makeText(context,prediction.name,Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context,"无匹配手势",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
