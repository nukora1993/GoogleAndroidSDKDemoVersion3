package com.example.googlesdkversion3demo.chapter4;

import android.app.PendingIntent;
import android.app.SearchManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.googlesdkversion3demo.R;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget_provider);
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
        Intent intent=new Intent(context,UpdateService.class);
        context.startService(intent);

        //widgetIds不是小组件中控件的id，而是所有的小组件
        final int N=appWidgetIds.length;
        for (int i = 0; i <N ; i++) {
            int appWidgetId=appWidgetIds[i];
            Intent configIntent=new Intent(context,Chapter4AppWidgetProviderActivity.class);
            PendingIntent configPendingIntent=PendingIntent.getActivity(context,0,configIntent,0);
            RemoteViews remoteViews=new RemoteViews(
                    context.getPackageName(),R.layout.remote_layout
            );

            //给指定控件加上点击事件
            remoteViews.setOnClickPendingIntent(R.id.button,configPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

