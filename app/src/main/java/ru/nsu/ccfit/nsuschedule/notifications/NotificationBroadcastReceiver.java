package ru.nsu.ccfit.nsuschedule.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Arrays;

import ru.nsu.ccfit.nsuschedule.AppContainer;
import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.ui.ScheduleEvent;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "ru.nsu.ccfit.nsuschedule.CHANNEL_ID_123143";
    private static final String CHANNEL_NAME = "Schedule notifications";
    private static final int NOTIFICATION_ID = 0;

    private PendingResult pendingResult;
    private AppContainer appContainer;

    @Override
    public void onReceive(Context context, Intent intent) {
        appContainer = ((ApplicationWithAppContainer) context.getApplicationContext()).getAppContainer();
        ScheduleEvent event = (ScheduleEvent) intent.getExtras().getSerializable(AndroidScheduleNotificationManager.EVENT_SERIALIZABLE_KEY);
        Notification.Builder builder = new Notification.Builder(context);
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID,
                            CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH));
        }
        Notification notification = builder
                .setContentTitle(formatTitle(event))
                .setContentText(formatText(event))
                .build();
        notificationManager.notify(NOTIFICATION_ID, notification);
        pendingResult = goAsync();
        new Thread(this::setNext).start();
    }

    private void setNext() {
        try {
            appContainer.setupNextNotification.invoke();
        } catch (RepositoryException e) {
            Log.e(getClass().getName(), Arrays.toString(e.getStackTrace()));
        }
        pendingResult.finish();
    }

    private CharSequence formatText(ScheduleEvent event) {
        return String.format("%s%n%s, %s",
                event.getDescription(),
                event.getTime(), event.getLocation()
        );
    }

    private CharSequence formatTitle(ScheduleEvent event) {
        return event.getSummary();
    }
}
