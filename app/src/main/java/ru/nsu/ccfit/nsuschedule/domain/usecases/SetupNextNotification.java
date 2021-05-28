package ru.nsu.ccfit.nsuschedule.domain.usecases;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.nsu.ccfit.nsuschedule.domain.ScheduleNotificationManager;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.domain.repository.SettingsRepository;
import ru.nsu.ccfit.nsuschedule.ui.ScheduleEvent;

public class SetupNextNotification {
    private final int notificationMinutesBeforeEvent;
    private final ScheduleNotificationManager notificationManager;
    private final Repository repository;
    private final DateFormat timeFormat;
    private final SettingsRepository settingsRepository;

    public SetupNextNotification(int notificationMinutesBeforeEvent,
                                 ScheduleNotificationManager notificationManager,
                                 Repository repository, DateFormat timeFormat, SettingsRepository settingsRepository) {
        this.notificationMinutesBeforeEvent = notificationMinutesBeforeEvent;
        this.notificationManager = notificationManager;
        this.repository = repository;
        this.timeFormat = timeFormat;
        this.settingsRepository = settingsRepository;
    }

    public void invoke() throws RepositoryException {
        if (!settingsRepository.notificationsEnabled()) return;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, notificationMinutesBeforeEvent);
        repository.getEventsInRange(now.getTime(), new Date(Long.MAX_VALUE))
                .stream().map(this::mapToScheduleEvent)
                .min(this::findMinComparator)
                .ifPresent(this::setNextNotification);
    }

    private int findMinComparator(ScheduleEvent o1, ScheduleEvent o2) {
        return o1.getStartTime().compareTo(o2.getStartTime());
    }

    private void setNextNotification(ScheduleEvent event) {
        Calendar c = Calendar.getInstance();
        c.setTime(event.getStartTime());
        c.add(Calendar.MINUTE, -notificationMinutesBeforeEvent);
        notificationManager.setNextNotification(c, event);
    }

    private ScheduleEvent mapToScheduleEvent(Event e) {
        return new ScheduleEvent(e,
                e.getDate().getFirstOccurrenceAfterDate(new Date()).orElse(null),
                timeFormat);
    }
}
