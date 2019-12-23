package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.googlesdkversion3demo.R;

import java.util.ArrayList;
import java.util.List;

public class Chapter5TasksActivity extends AppCompatActivity {
    private Button button;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayListTask;
    private ActivityManager activityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_tasks);

        button=findViewById(R.id.button);
        listView=findViewById(R.id.list_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityManager=(ActivityManager)getApplicationContext()
                        .getSystemService(ACTIVITY_SERVICE);
                arrayListTask=new ArrayList<String>();


                List<ActivityManager.RunningTaskInfo> runningTaskInfos=
                        activityManager.getRunningTasks(999);
                int i=1;
                //baseActivity时任务栈中第一个Activity
                for(ActivityManager.RunningTaskInfo amTask:runningTaskInfos){
                    arrayListTask.add(""+(i++)+":"+amTask.baseActivity.getClassName()+"(ID="+amTask.id+")");
                }

                //获得service
//                List<ActivityManager.RunningServiceInfo> runningServiceInfos=
//                        activityManager.getRunningServices(999);
//
//                for(ActivityManager.RunningServiceInfo amTask:runningServiceInfos){
//                    arrayListTask.add(amTask.process+"(ID="+amTask.pid+")");
//                }

                arrayAdapter=new ArrayAdapter<String>(Chapter5TasksActivity.this,
                        android.R.layout.simple_list_item_1,arrayListTask);

                listView.setAdapter(arrayAdapter);
            }
        });

    }
}
