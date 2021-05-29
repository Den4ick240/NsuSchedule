package ru.nsu.ccfit.nsuschedule.domain.usecases;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.ScheduleNotificationManager;
import ru.nsu.ccfit.nsuschedule.domain.TestRepository;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventDate;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventInfo;
import ru.nsu.ccfit.nsuschedule.domain.entities.Repeating;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.domain.repository.SettingsRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


class SetupNextNotificationTest {

    @Test
    void setupTest() throws RepositoryException {
        int minutesBefore = 10;
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MINUTE,  10);
        Date expectedNotificationTime = time.getTime();
        time.add(Calendar.MINUTE, minutesBefore);
        List<Event> eventList = new ArrayList<>();
        Event testEvent = new Event(new EventInfo("", "", ""),
                new EventDate(time.getTime(), time.getTime(), Repeating.ONCE));
        eventList.add(testEvent);
        Repository repository = new TestRepository() {
            @Override
            public List<Event> getEventsInRange(Date startDate, Date endDate) throws RepositoryException {
                return eventList;
            }
        };
        ScheduleNotificationManager notificationManager = (notificationTime, alarmOn, event) -> {
            assertThat(alarmOn, is(false));
            assertThat(event.getEvent(), equalTo(testEvent));
            assertThat(notificationTime.getTime(), equalTo(expectedNotificationTime));
        };

        new SetupNextNotification(minutesBefore, notificationManager, repository, DateFormat.getInstance(), new SettingsRepository() {
            @Override
            public boolean notificationsEnabled() {
                return true;
            }
            @Override
            public boolean alarmsEnabled() {
                return true;
            }
            @Override
            public void setAlarmsEnabled(boolean alarmsEnabled) {}
            @Override
            public void setNotificationsEnabled(boolean notificationsEnabled) {}
        }).invoke();
    }


}