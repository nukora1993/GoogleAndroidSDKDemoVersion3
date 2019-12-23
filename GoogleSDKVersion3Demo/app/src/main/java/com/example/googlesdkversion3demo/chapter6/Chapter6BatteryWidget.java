package com.example.googlesdkversion3demo.chapter6;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import com.example.googlesdkversion3demo.R;

/**
 * Implementation of App Widget functionality.
 */
public class Chapter6BatteryWidget extends AppWidgetProvider {
    private static final String MY_PREFS="SHARED_PREF";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.chapter6_batter_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
        context.startService(new Intent(context, UpdateService.class));
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static class UpdateService extends Service {
        private MyBroadcastReceiver receiver;

        //非常坑爹，Service必须要要有一个public的不带参数的构造函数
        public UpdateService(){
            super();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            //注册receiver
            IntentFilter filter;
            filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            receiver=new MyBroadcastReceiver();
            registerReceiver(receiver,filter);

            RemoteViews updateViews=keepUpdate(this);
            ComponentName thisWidget=new ComponentName(this, Chapter6BatteryWidget.class);

            AppWidgetManager manager=AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget,updateViews);

            return super.onStartCommand(intent, flags, startId);
        }

        private RemoteViews keepUpdate(Context context){
            RemoteViews remoteViews=null;
            remoteViews=new RemoteViews(context.getPackageName(),
                    R.layout.update_widget);

            int power=0;
            SharedPreferences preferences=context.getSharedPreferences(MY_PREFS,
                    MODE_PRIVATE);
            if(preferences!=null){
                power=preferences.getInt("power",0);
            }

            remoteViews.setTextViewText(R.id.text_view,power+"%");

            return remoteViews;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onDestroy() {
            unregisterReceiver(receiver);
            super.onDestroy();
        }

        class MyBroadcastReceiver extends BroadcastReceiver{
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                    //level表示当前电量，scale表示最大电量
                    int level=intent.getIntExtra("level",0);
                    int scale=intent.getIntExtra("scale",100);
                    SharedPreferences preferences=context.getSharedPreferences(
                            MY_PREFS,Context.MODE_PRIVATE
                    );
                    if(preferences!=null){
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putInt("power",(level*100/scale));
                        editor.commit();
                    }
                }
            }
        }
    }


}

