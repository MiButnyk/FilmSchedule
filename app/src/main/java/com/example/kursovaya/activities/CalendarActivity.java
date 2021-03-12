package com.example.kursovaya.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TimePicker;

import com.example.kursovaya.support_classes.NotificationPublisher;
import com.example.kursovaya.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.kursovaya.support_classes.NotificationPublisher.Id;

public class CalendarActivity extends AppCompatActivity {

    public static String WATCHING_KEY = "WATCHING_KEY";
    public static String SAVED_ID = "SAVED_ID";
    public static String ID_FILM = "ID_FILM";

    private CalendarView calendarView;
    private AlarmManager am;
    private NotificationCompat.Builder builder;
    private Bundle bundle;
    private int nid = 0;
    private SharedPreferences sPref;

    private int hour = 12;
    private int minutes = 0;

    private Notification getNotification(String contentText) {
        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent(this, FilmDetailsActivity.class);
        intent.putExtra(FilmDetailsActivity.ID_KEY, (int)bundle.get(ID_FILM));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, nid, intent, 0);
        return builder.setContentText(contentText)
                .setSmallIcon(R.drawable.ic_watch_later_black_24dp)
                .setContentTitle("Time to watch!")
                .setAutoCancel(true)
                .setShowWhen(false)
                .setContentIntent(pendingIntent)
                .build();
    }

    private void scheduleNotification(Notification notification, long date) {

        Intent notificationIntent = new Intent(CalendarActivity.this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, nid);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, nid, notificationIntent, 0);

        am.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);
        nid += 1;
        if (nid > 214748364) {
            nid = -214748364;
        }
        saveId();
    }


    private void saveId() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_ID, String.valueOf(nid));
        ed.apply();
    }

    private void loadId() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedId = sPref.getString(SAVED_ID, "0");
        nid = Integer.parseInt(savedId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        loadId();
        calendarView = findViewById(R.id.calendar_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, Id);
        }
        else {
            builder = new NotificationCompat.Builder(this);
        }
        am = (AlarmManager) getSystemService(ALARM_SERVICE);

        calendarView.getDateTextAppearance();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                TimePickerDialog.OnTimeSetListener timeCallBack = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minute;
                        bundle = getIntent().getExtras();
                        Date d = new Date();
                        String dd = year + "/" + (month + 1) + "/" + dayOfMonth + ", " + hour + ":" + minutes;
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd, HH:mm");
                        try {
                            d = formatter.parse(dd);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long millis = d.getTime();
                        scheduleNotification(getNotification(bundle.get(WATCHING_KEY) +" movie is scheduled for today"), millis);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarActivity.this, TimePickerDialog.THEME_HOLO_DARK, timeCallBack, hour, minutes, true);
                timePickerDialog.setTitle("Choose notification time");
                timePickerDialog.show();
            }
        });
    }
}
