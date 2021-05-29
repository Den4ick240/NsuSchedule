package ru.nsu.ccfit.nsuschedule.domain;

import java.util.Calendar;

import ru.nsu.ccfit.nsuschedule.domain.entities.ScheduleEvent;

public interface ScheduleNotificationManager {
    void setNextNotification(Calendar notificationTime, boolean alarmOn, ScheduleEvent event);
}
