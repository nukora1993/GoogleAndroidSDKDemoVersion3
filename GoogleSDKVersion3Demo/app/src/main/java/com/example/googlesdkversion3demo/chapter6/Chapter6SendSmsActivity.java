package com.example.googlesdkversion3demo.chapter6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

public class Chapter6SendSmsActivity extends AppCompatActivity {
    private Button button1,button2;
    private TextView textView1,textView2,textView3;
    private String message;

    private static final int PICK_CONTACT_SUB_ACTIVITY=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},0);
            }
            if(checkSelfPermission(Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
            }
        }
        setContentView(R.layout.activity_chapter6_send_sms);
        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);

        textView1=findViewById(R.id.text_view_1);
        textView2=findViewById(R.id.text_view_2);
        textView3=findViewById(R.id.text_view_3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://contacts/people");
                Intent intent=new Intent(Intent.ACTION_PICK,uri);
                message=textView1.getText().toString();
                startActivityForResult(intent,PICK_CONTACT_SUB_ACTIVITY);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://contact/people");
                Intent intent=new Intent(Intent.ACTION_PICK,uri);
                message=textView2.getText().toString();
                startActivityForResult(intent,PICK_CONTACT_SUB_ACTIVITY);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case PICK_CONTACT_SUB_ACTIVITY:
                Uri uriRet=null;
                try{
                    uriRet=data.getData();
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(uriRet!=null){
                    try{
                        //选中之后返回一个uri，通过该uri可以获得联系人id，通过联系人id可以获得具体字段,这里可以读取到所有联系人id，但只选取1个测试
                        Cursor c=managedQuery(uriRet,null,null,null,null);
                        c.moveToFirst();
                        String name="";
                        String phone="";
                        //联系人id
                        int contactId=c.getInt(
                                c.getColumnIndex(ContactsContract.Contacts._ID)
                        );

                        Cursor curContacts=getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,
                                null,null
                        );

                        if(curContacts.getCount()>0){
                            curContacts.moveToFirst();
                            name=curContacts.getString(
                                    curContacts.getColumnIndex(
                                            ContactsContract.Contacts.DISPLAY_NAME
                                    )
                            );
                            phone=curContacts.getString(
                                    curContacts.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.NUMBER
                                    )
                            );
                        }else{

                        }

                        String destAddress=phone;
                        SmsManager smsManager=SmsManager.getDefault();
                        PendingIntent pi=PendingIntent.getBroadcast(
                                Chapter6SendSmsActivity.this,0,new Intent(),0
                        );

                        smsManager.sendTextMessage(destAddress,null,message,pi,null);
                        Toast.makeText(Chapter6SendSmsActivity.this,"发给："+name,Toast.LENGTH_SHORT).show();
                        textView3.setText(name+":"+phone);

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

        }

        super.onActivityResult(requestCode,resultCode,data);
    }
}
