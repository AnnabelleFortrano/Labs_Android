package com.example.lab4;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class MyWidget extends AppWidgetProvider {

    final static String NAME = "widgetInfo";
    final static String COUNT_OF_DAYS = "countOfDays";
    final static String DATE = "date";
    final String LOG_TAG = "myLogs";
    private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        //цикл по всем созданным виджетам
        for (int i = 0; i < appWidgetIds.length; i++) {
            int widgetID = appWidgetIds[i];

            //формирует интент для обработки нажатий
            Intent configIntent = new Intent(context, CalendarDialog.class);
            configIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
            configIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pIntent = PendingIntent.getActivity(context, widgetID, configIntent, 0);

            //находит view виджета, устанавливет intent для нажатий, обновляет виджет
            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);
            widgetView.setOnClickPendingIntent(R.id.rl_widget, pIntent);
            appWidgetManager.updateAppWidget(widgetID, widgetView);

            //считывает из SharedPreferences инфу о дате
            SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
            String textDate = sp.getString(DATE + widgetID, "00.00.0000");

            //если дата не дефолтная
            if (!textDate.equals("00.00.0000")) {
                Calendar thisDate = Calendar.getInstance();
                Calendar chooseDate = Calendar.getInstance();
                try {
                    //устанавливает calendar по дате из SP
                    chooseDate.clear();
                    chooseDate.setTime(format.parse(textDate));
                    chooseDate.set(Calendar.HOUR, 9);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //если дата в прошлом, то выводим уведомление
                if (thisDate.after(chooseDate)) {
                    NotificationWidget notificationWidget = new NotificationWidget(context);
                    notificationWidget.sendNotification();
                } else { //если в будущем, то устанавливаем alarm
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context, MyWidget.class);
                    intent.setAction("Alarm");
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, widgetID, intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, chooseDate.getTime().getTime(), alarmIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, chooseDate.getTime().getTime(), alarmIntent);
                    } else {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, chooseDate.getTime().getTime(), alarmIntent);
                    }
                }
                updateWidget(context, widgetID, chooseDate);

            } else {
                widgetView.setTextViewText(R.id.tv_date, textDate);
                widgetView.setTextViewText(R.id.tv_countDays, "0");
                appWidgetManager.updateAppWidget(widgetID, widgetView);
            }
        }

        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
    }

    public static void updateWidget(Context context, int widgetID, Calendar chooseDate) {
        int countDays = calculationCountOfDays(chooseDate);
        //считывает инфу из SP
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DATE + widgetID, format.format(chooseDate.getTime())).commit();
        sp.edit().putInt(COUNT_OF_DAYS + widgetID, countDays).commit();

        Log.d("myLogs", "updateWidget: " + countDays);
        // Обновляет виджет
        RemoteViews widgetView = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        widgetView.setTextViewText(R.id.tv_date, format.format(chooseDate.getTime()));
        widgetView.setTextViewText(R.id.tv_countDays, String.valueOf(countDays));
        AppWidgetManager.getInstance(context).updateAppWidget(widgetID, widgetView);
    }

    //обновляает виджет после будильника
    private void updateAfterAlarm(Context context, int widgetID) {
        //записывает инфу в SP
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(COUNT_OF_DAYS + widgetID, 0).commit();

        // Обновляем виджет
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);
        widgetView.setTextViewText(R.id.tv_countDays, "0");
        AppWidgetManager.getInstance(context).updateAppWidget(widgetID, widgetView);
    }

    //считает кол-во дней до даты
    private static int calculationCountOfDays(Calendar chooseDate) {
        int countDays = 0;
        Calendar thisDate = Calendar.getInstance();
        if (chooseDate.after(thisDate)) {
            long millis = chooseDate.getTime().getTime() - thisDate.getTime().getTime();
            countDays = (int) ((millis / (24 * 60 * 60 * 1000)) + 1);
        }
        return countDays;
    }

    //отправляет уведомление, обновляет виджет
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("Alarm")) {
            NotificationWidget notificationWidget = new NotificationWidget(context);
            notificationWidget.sendNotification();

            int widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
            updateAfterAlarm(context, widgetID);
        }
    }

    //при удалении виджета отменяет alarm и удаляем данные из SharedPreferences
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for (int i = 0; i < appWidgetIds.length; i++) {
            int widgetID = appWidgetIds[i];
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MyWidget.class);
            intent.setAction("Alarm");
            PendingIntent pIntent = PendingIntent.getBroadcast(context, widgetID, intent, 0);
            alarmManager.cancel(pIntent);

            SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
            sp.edit().remove(DATE + widgetID).commit();
            sp.edit().remove(COUNT_OF_DAYS + widgetID).commit();
        }
    }

}