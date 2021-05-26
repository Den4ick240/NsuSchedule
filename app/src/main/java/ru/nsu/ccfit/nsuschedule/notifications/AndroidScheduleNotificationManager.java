package ru.nsu.ccfit.nsuschedule.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import ru.nsu.ccfit.nsuschedule.domain.ScheduleNotificationManager;
import ru.nsu.ccfit.nsuschedule.ui.ScheduleEvent;

public class AndroidScheduleNotificationManager implements ScheduleNotificationManager {
    public static final String EVENT_SERIALIZABLE_KEY = "EVENT_KEY";
    private final Context context;
    private static final int BROADCAST_REQUEST_CODE = 100;

    public AndroidScheduleNotificationManager(Context context) {
        this.context = context;
    }

    @Override
    public void setNextNotification(Calendar notificationTime, ScheduleEvent event) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(context, NotificationBroadcastReceiver.class);
        notificationIntent.putExtra(EVENT_SERIALIZABLE_KEY, event);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, BROADCAST_REQUEST_CODE,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTime.getTimeInMillis(), broadcast);
    }
}
