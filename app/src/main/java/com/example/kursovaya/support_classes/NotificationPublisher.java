package com.example.kursovaya.support_classes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;


public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static String Id = "1";
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Id, "Main channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Main channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            nm.createNotificationChannel(channel);
        }
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        Bundle bundle = intent.getExtras();
        int id = (Integer)bundle.get(NOTIFICATION_ID);
        nm.notify(id, notification);

    }
}