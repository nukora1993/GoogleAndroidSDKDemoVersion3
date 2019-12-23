package com.example.googlesdkversion3demo.chapter5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

public class Chapter5GetContactActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    private EditText editText1;
    private EditText editText2;
    private static final int PICK_CONTACT_SUBACTIVITY=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_get_contatc);

        textView=findViewById(R.id.text_view);
        editText1=findViewById(R.id.edit_text1);
        editText2=findViewById(R.id.edit_text2);

        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(Intent.ACTION_PICK,
                                ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT_SUBACTIVITY
                );
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            switch(requestCode){
                //选择之后会返回一个URI，使用该URI去获取数据
                case PICK_CONTACT_SUBACTIVITY:
                    final Uri uriRet=data.getData();
                    if(uriRet!=null){
                        //managedQuery实际上还是使用了contentResolver，并且Activity会管理cursor的生命周期
                        Cursor c=managedQuery(uriRet,null,null,null,null);
                        c.moveToFirst();
                        String strName=c.getString(
                                c.getColumnIndexOrThrow(
                                        ContactsContract.Contacts.DISPLAY_NAME
                                )
                        );

                        editText1.setText(strName);

                        int contactId=c.getInt(
                                c.getColumnIndex(ContactsContract.Contacts._ID)
                        );

                        //查询到的手机号
                        Cursor phones=getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+contactId,
                                null,null
                        );

                        StringBuffer sb=new StringBuffer();
                        int typePhone,resType;
                        String numPhone;
                        //同一个人可以有多个手机号，这里只显示一个
                        if(phones.getCount()>0){
                            phones.moveToFirst();
                            typePhone=phones.getInt(
                                    phones.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.TYPE
                                    )
                            );

                            numPhone=phones.getString(
                                    phones.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.NUMBER
                                    )
                            );

                            resType=ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(typePhone);
                            sb.append(getString(resType)+":"+numPhone+"\n");
                            editText2.setText(numPhone);
                        }else{
                            sb.append("no phone");
                        }

                        Toast.makeText(this,sb.toString(),Toast.LENGTH_SHORT).show();

                    }
            }
        }catch(Exception e){
            textView.setText(e.toString());
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
