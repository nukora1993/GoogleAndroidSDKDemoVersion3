package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

public class Chapter5AutoCompleteContactActivity extends AppCompatActivity {
    private static final String TAG="Chapter5Log";

    private AutoCompleteTextView autoCompleteTextView;
    private TextView textView;
    private Cursor cursor;
    private ContactsAdapter contactsAdapter;

    public static final String[] PHONE_PROJECTION=new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.DISPLAY_NAME

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_chapter5_auto_complet_contact);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},0);
            }
        }

        autoCompleteTextView=findViewById(R.id.auto_complete_text_view);
        textView=findViewById(R.id.text_view);

        ContentResolver contentResolver=getContentResolver();
        //刚开始全部查出
        cursor=contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONE_PROJECTION,
                null,null,""
        );


        contactsAdapter=new ContactsAdapter(this,cursor);
        autoCompleteTextView.setAdapter(contactsAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cursor c=contactsAdapter.getCursor();
                        //移动到指定位置
                        c.moveToPosition(position);
                        String number=c.getString(
                                c.getColumnIndexOrThrow(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                        );
                        number=number==null?"无输入电话":number;
                        textView.setText(c.getString(
                                c.getColumnIndexOrThrow(
                                        ContactsContract.Contacts.DISPLAY_NAME
                                )
                        )+" 的电话是"+number);
                    }
                }
        );
    }





    //我们知道有ArrayAdapter，表示数据源是Array
    //那么CursorAdapter，表示数据源是Cursor
    //但是不知道为什么，getView死活不调用
    class ContactsAdapter extends CursorAdapter{
        private ContentResolver contentResolver;

        public ContactsAdapter(Context context, Cursor cursor){
            super(context,cursor);
            contentResolver=context.getContentResolver();
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Log.d(TAG,"bindView");
            ((TextView)view).setText(
                    cursor.getColumnIndexOrThrow(
                            ContactsContract.Contacts.DISPLAY_NAME
                    )
            );
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            Log.d(TAG,"needView");
            final LayoutInflater inflater=LayoutInflater.from(context);
            final TextView view=(TextView)inflater.inflate(
                    android.R.layout.simple_dropdown_item_1line,parent,false
            );

            view.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    ContactsContract.Contacts.DISPLAY_NAME
                            )
                    )
            );
            return view;
        }

        @Override
        public CharSequence convertToString(Cursor cursor) {
            Log.d(TAG,"convertToString");
            return cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            ContactsContract.Contacts.DISPLAY_NAME
                    )
            );
        }

        @Override
        public int getCount() {
            int count=super.getCount();
            Log.d(TAG,"getCount:"+count);
            return count;
        }


        //当在框中输入文字时会调用该方法
        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            Log.d(TAG,"runQuery");
            if(getFilterQueryProvider()!=null){
                return getFilterQueryProvider().runQuery(constraint);
            }

            StringBuilder buffer=null;
            String[] args=null;
            if(constraint!=null){
                buffer=new StringBuilder();
                buffer.append("UPPER(");
                buffer.append(ContactsContract.Contacts.DISPLAY_NAME);
                buffer.append(") GLOB ?");
                args=new String[]{
                        constraint.toString().toUpperCase()+"*"
                };
            }

            Cursor cursor=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    PHONE_PROJECTION,
                    buffer==null?null:buffer.toString(),
                    args,
                    "");
            Log.d(TAG,"cursor count:"+cursor.getCount());
            return cursor;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG,"getView");
            return super.getView(position, convertView, parent);
        }
    }
}
