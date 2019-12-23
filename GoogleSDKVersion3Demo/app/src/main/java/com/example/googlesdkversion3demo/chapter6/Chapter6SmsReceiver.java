package com.example.googlesdkversion3demo.chapter6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Chapter6SmsReceiver extends BroadcastReceiver {
    private static final String ACTION="android.provider.Telephony.SMS_RECEIVED";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        if(intent.getAction().equals(ACTION)){
            StringBuilder sb=new StringBuilder();
            Bundle bundle=intent.getExtras();
            if(bundle!=null){
                //puds为android内置的短信标志
                Object[] myObjPdus=(Object[])bundle.get("pdus");
                SmsMessage[] messages=new SmsMessage[myObjPdus.length];
                for (int i = 0; i <myObjPdus.length ; i++) {
                    messages[i]=SmsMessage.createFromPdu((byte[])myObjPdus[i]);
                }

                for(SmsMessage currentMessage:messages){
                    sb.append("接收到来自:\n");
                    sb.append(currentMessage.getDisplayOriginatingAddress());
                    sb.append("\n发来的短信:\n");
                    sb.append(currentMessage.getDisplayMessageBody());
                }

                Toast.makeText(context,sb.toString(),Toast.LENGTH_LONG).show();

                //打开主Activity
                Intent i=new Intent(context,Chapter6SmsServiceActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
