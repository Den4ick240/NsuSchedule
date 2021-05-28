package ru.nsu.ccfit.nsuschedule.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import ru.nsu.ccfit.nsuschedule.domain.ScheduleNotificationManager;
import ru.nsu.ccfit.nsuschedule.ui.ScheduleEvent;

public class AndroidScheduleNotificationManager implements ScheduleNotificationManager {
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String CONTENT_KEY = "CONTENT_KEY";
    public static final String ALARMS_KEY = "ALARMS_KEY";

    private static final int BROADCAST_REQUEST_CODE = 100;
    private final Context context;

    public AndroidScheduleNotificationManager(Context context) {
        this.context = context;
    }

    @Override
    public void setNextNotification(Calendar notificationTime, boolean alarmOn, ScheduleEvent event) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(context, NotificationBroadcastReceiver.class);

        notificationIntent.putExtra(TITLE_KEY, formatTitle(event));
        notificationIntent.putExtra(CONTENT_KEY, formatText(event));
        notificationIntent.putExtra(ALARMS_KEY, alarmOn);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, BROADCAST_REQUEST_CODE,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTime.getTimeInMillis(), broadcast);
    }

    private CharSequence formatText(ScheduleEvent event) {
        return String.format("%s, %s %n%s"
                , event.getLocation()
                , event.getTime()
                , event.getDescription()
        );
    }

    private CharSequence formatTitle(ScheduleEvent event) {
        return event.getSummary();
    }
}
