package com.example.googlesdkversion3demo.chapter4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

public class Chapter4ListActivity extends ListActivity {
    private static final String TAG="ListActivityLog";
    private int selectedItem=-1;
    private String[] mString;
    static final private int MENU_LIST1= Menu.FIRST;
    static final private int MENU_LIST2=Menu.FIRST+1;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chapter4_list);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        selectedItem=position;
        Toast.makeText(this,mString[selectedItem], Toast.LENGTH_LONG).show();
        super.onListItemClick(l, v, position, id);
    }

//    不知道为什么，该方法无法调用，显示的Activity也没有菜单项
    //将该Activity的Theme指定为@android:style/Theme.Holo.Light即可显示
    //所以Theme应该控制着Activity的DecorView和Menu的显示，Theme和Activity有着较强的耦合关系
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG,"onCreateOptionsMenu");
        int idGroup=0;
        int orderMenuItem1=Menu.NONE;
        int orderMenuItem2=Menu.NONE+1;

        menu.add(idGroup,MENU_LIST1,orderMenuItem1,"aa");
        menu.add(idGroup,MENU_LIST2,orderMenuItem2,"bb");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case MENU_LIST1:
                mString=new String[]{"aa","bb","cc","dd"};
                adapter=new ArrayAdapter<String>(this,R.layout.main,mString);
                setListAdapter(adapter);
                break;
            case MENU_LIST2:
                mString=new String[]{"dd","cc","bb","aa"};
                adapter=new ArrayAdapter<>(this,R.layout.main,mString);
                setListAdapter(adapter);
                break;
                default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}
