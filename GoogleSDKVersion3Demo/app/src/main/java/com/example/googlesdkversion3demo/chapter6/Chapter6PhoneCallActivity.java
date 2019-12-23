package com.example.googlesdkversion3demo.chapter6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter6PhoneCallActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter6_phone_call);
        textView=findViewById(R.id.text_view);
        MyPhoneCallListener myPhoneCallListener=new MyPhoneCallListener();

        //监听电话呼入状态
        TelephonyManager telephonyManager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(myPhoneCallListener,PhoneStateListener.LISTEN_CALL_STATE);


    }

    class MyPhoneCallListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    textView.setTextColor(Color.RED);
                    textView.setText("IDLE");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    textView.setTextColor(Color.GREEN);
                    textView.setText("OFFHOOK");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    getContactPeople(phoneNumber);
                    break;
                default:break;
            }
        }

    }

    private void getContactPeople(String phoneNumber){
        textView.setTextColor(Color.BLUE);
        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=null;
        String[] projection=new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        //获得手机中存储的联系人信息
        cursor=contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                ContactsContract.CommonDataKinds.Phone.NUMBER+"=?",
                new String[]{phoneNumber},""
        );

        if(cursor.getCount()==0){
            textView.setText("unknown number:"+phoneNumber);
        }else{
            cursor.moveToFirst();
            String name=cursor.getString(1);
            textView.setText(name+":"+phoneNumber);
        }

    }
}
