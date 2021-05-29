package ru.nsu.ccfit.nsuschedule.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
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
import ru.nsu.ccfit.nsuschedule.domain.entities.ScheduleEvent;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.domain.repository.SettingsRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class GetEventsForDayTest {
    Date testStartDate;
    Date testEndDate;
    List<Event> eventList;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Calendar.FEBRUARY, 1, 1, 1, 1);
        testStartDate = calendar.getTime();
        calendar.set(1, Calendar.FEBRUARY, 2, 1, 1, 1);
        testEndDate = calendar.getTime();
        eventList = new ArrayList<>();
        eventList.add(new Event());
    }


    @Test
    void getEvents() {
        Repository repository = new TestRepository() {
            @Override
            public List<Event> getEventsInRange(Date startDate, Date endDate) throws RepositoryException {
                assertThat(startDate, equalTo(testStartDate));
                assertThat(endDate, equalTo(testEndDate));
                return eventList;
            }
        };
        assertThat(new GetEventsForDay(repository).getEvents(testStartDate), equalTo(eventList));
    }
}