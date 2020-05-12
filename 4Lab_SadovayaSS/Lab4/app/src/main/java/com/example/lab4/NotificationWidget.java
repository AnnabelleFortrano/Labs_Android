package com.example.lab4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationWidget {

    final private String CHANNEL_NAME = "Основной";
    final private String CHANNEL_ID = "5";
    final private int NOTIF_ID = 5;

    private Context context;

    public NotificationWidget(Context context) {
        this.context = context;
    }

    //метод создает канал для уведомлений
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //метод отправляет уведомления
    public void sendNotification() {

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Уведомление")
                .setContentText("Вы просили напомнить о дате!")
                .setVibrate(new long[] {500, 500, 500, 500})
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIF_ID, builder.build());
    }

}
