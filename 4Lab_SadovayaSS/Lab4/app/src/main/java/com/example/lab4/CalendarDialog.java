package com.example.lab4;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.Window;
import android.widget.DatePicker;

import java.util.Calendar;

public class CalendarDialog extends Activity {

    private DatePickerDialog dateDialog;
    private int widgetID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //убирает title activity
        setContentView(R.layout.activity_calendar_dialog);

        //получаем из intent'a ID виджета
        this.widgetID = getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

        //создаем datePickerDialog c сегодняшней датой
        Calendar thisDate = Calendar.getInstance();
        dateDialog = new DatePickerDialog(this, dateDialogListener, thisDate.get(Calendar.YEAR), thisDate.get(Calendar.MONTH), thisDate.get(Calendar.DAY_OF_MONTH));
        dateDialog.setCancelable(false); //блокирует кнопку назад

        //слушатель для отмены диалога
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                killProcessApp(); //убивает процесс, чтоб не осталось открытой вкладки
            }
        };
        dateDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Отмена", listener);

        dateDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateDialogListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int month,
                              int day) {

            Calendar chooseDate = Calendar.getInstance();
            chooseDate.set(year, month, day, 9, 0, 0);

            //создает alarm для отправки уведомления
            AlarmManager alarmManager = (AlarmManager) CalendarDialog.this.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(CalendarDialog.this, MyWidget.class);
            intent.setAction("Alarm");
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
            PendingIntent pIntent = PendingIntent.getBroadcast(CalendarDialog.this, widgetID, intent, 0);

            //в зависимости от версий SDK запускает наиболее точный alarm
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, chooseDate.getTime().getTime(), pIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, chooseDate.getTime().getTime(), pIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, chooseDate.getTime().getTime(), pIntent);
            }

            //обновляем виджет и убиваем процесс
            MyWidget.updateWidget(CalendarDialog.this, widgetID, chooseDate);
            killProcessApp();
        }

    };

    //метод "убивает" процесс
    private void killProcessApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAndRemoveTask();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                finish();
            }
        }
        Process.killProcess(Process.myPid());
    }
}
