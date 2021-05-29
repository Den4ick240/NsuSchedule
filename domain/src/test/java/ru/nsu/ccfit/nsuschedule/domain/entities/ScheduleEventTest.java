package ru.nsu.ccfit.nsuschedule.domain.entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class ScheduleEventTest {
    ScheduleEvent scheduleEvent;
    Date startDate;
    Date endDate;

    @BeforeEach
    void setUp() {
        DateFormat format = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Calendar.FEBRUARY, 1, 1, 0, 0);
        startDate = calendar.getTime();
        calendar.add(Calendar.HOUR, 1);
        endDate = calendar.getTime();
        Event event = new Event(new EventInfo("summary", "description", "location"),
                new EventDate(startDate, endDate, Repeating.ONCE));
        EventOccurrence occurrence = event.getDate().getFirstOccurrenceAfterDate(startDate).get();
        scheduleEvent = new ScheduleEvent(event, occurrence, format);
    }

    @Test
    void startDateTest() {
        assertThat(scheduleEvent.getStartTime(), is(equalTo(startDate)));
    }

    @Test
    void dateFormatTest() {
        assertThat(scheduleEvent.getTime(), is(equalTo("01:00-02:00")));
    }

    @Test
    void infoFieldTest() {
        assertThat(scheduleEvent.getSummary(), equalTo("summary"));
        assertThat(scheduleEvent.getDescription(), equalTo("description"));
        assertThat(scheduleEvent.getLocation(), equalTo("location"));
    }

}