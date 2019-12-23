package com.example.googlesdkversion3demo.chapter4;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.googlesdkversion3demo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateService extends Service {
    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        RemoteViews updateViews=new RemoteViews(getPackageName(), R.layout.remote_layout);
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
        updateViews.setTextViewText(
                R.id.text_view,""+sdf.format(new Date())
        );

        ComponentName thisWidget=new ComponentName(this,MyAppWidgetProvider.class);
        AppWidgetManager manager=AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget,updateViews);
        return super.onStartCommand(intent, flags, startId);

    }
}
