package com.example.googlesdkversion3demo.chapter4;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.googlesdkversion3demo.R;

/**
 * Implementation of App Widget functionality.
 */
public class Chapter4UIAppWidgetProvider extends AppWidgetProvider {
    final String addButtonActionName="button1";
    final String removeButonActionName="button2";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.chapter4_uiapp_widget_provider);
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
        Toast.makeText(context,"onUpdate",Toast.LENGTH_SHORT).show();

        RemoteViews mainViews=new RemoteViews(
                context.getPackageName(),R.layout.remote_layout2
        );

        //当按下按钮时，会发送一个广播给小组件（注意小组件的类本质是receiver)
        Intent addIntent=new Intent(context,Chapter4UIAppWidgetProvider.class);
        addIntent.setAction(addButtonActionName);

        PendingIntent addPendingIntent=PendingIntent.getBroadcast(context,0,addIntent,0);
        //给按钮增加点击事件
        mainViews.setOnClickPendingIntent(R.id.button_1,addPendingIntent);

        Intent removeIntent=new Intent(context,Chapter4UIAppWidgetProvider.class);
        removeIntent.setAction(removeButonActionName);
        PendingIntent removePendingIntent=PendingIntent.getBroadcast(context,0,removeIntent,0);
        mainViews.setOnClickPendingIntent(
                R.id.button_2,removePendingIntent
        );

        appWidgetManager.updateAppWidget(appWidgetIds,mainViews);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,intent.getAction(),Toast.LENGTH_SHORT).show();
        if(intent.getAction().equals(addButtonActionName)){
            RemoteViews mainViews=new RemoteViews(context.getPackageName(),R.layout.remote_layout2);
            //清空linearlayout中的views
            mainViews.removeAllViews(R.id.linear_layout);

            RemoteViews subViews=new RemoteViews(
                    context.getPackageName(),R.layout.sub_layout
            );
            //将新的views加入到linearlayout
            mainViews.addView(R.id.linear_layout,subViews);

            ComponentName thisWidget=new ComponentName(context,Chapter4UIAppWidgetProvider.class);

            AppWidgetManager manager=AppWidgetManager.getInstance(context);
            manager.updateAppWidget(thisWidget,mainViews);
        }else if(intent.getAction().equals(removeButonActionName)){
            RemoteViews mainViews=new RemoteViews(context.getPackageName(),R.layout.remote_layout2);
            mainViews.removeAllViews(R.id.linear_layout);

            ComponentName thisWidget=new ComponentName(context,Chapter4UIAppWidgetProvider.class);

            AppWidgetManager manager=AppWidgetManager.getInstance(context);
            manager.updateAppWidget(thisWidget,mainViews);
        }
        super.onReceive(context, intent);
    }
}

