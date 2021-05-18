package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class EventOccurrenceTest {
    private EventOccurrence eventOccurrence;
    Calendar calendar;
    Date startDate, endDate;

    @BeforeEach
    void setUp() {
        calendar = Calendar.getInstance();
        calendar.set(1, 1, 1, 0, 0);
        startDate = calendar.getTime();
        calendar.set(1, 1, 1, 1, 0);
        endDate = calendar.getTime();
        eventOccurrence = new EventOccurrence(startDate, endDate);
    }


    @Test
    void startsBefore() {
        assertThat(eventOccurrence.startsBefore(calendar.getTime()), is(true));
    }

    @Test
    void startsAfter() {
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -1);
        assertThat(calendar.getTime().before(eventOccurrence.getStartDate()), is(true));
        assertThat(eventOccurrence.startsAfter(calendar.getTime()), is(true));
    }

    @Test
    void add() {
        EventOccurrence secondOccurrence = new EventOccurrence(eventOccurrence);
        secondOccurrence.add(Repeating.MONTH.dateInterval, Repeating.MONTH.amount);
        assertThat(eventOccurrence.getStart(Calendar.DATE), equalTo(secondOccurrence.getStart(Calendar.DATE)));
        assertThat(eventOccurrence.getStart(Calendar.MONTH) + Repeating.MONTH.amount,
                equalTo(secondOccurrence.getStart(Calendar.MONTH)));
    }

    @Test
    void addInMillis() {
        Repeating repeating = Repeating.WEEK;
        EventOccurrence secondOccurrence = new EventOccurrence(eventOccurrence);
        secondOccurrence.addInMillis(repeating.getInterval());
        assertThat(eventOccurrence.getStart(Calendar.DAY_OF_WEEK), equalTo(secondOccurrence.getStart(Calendar.DAY_OF_WEEK)));
        assertThat(eventOccurrence.getStart(repeating.dateInterval) + repeating.amount,
                equalTo(secondOccurrence.getStart(repeating.dateInterval)));
    }

    @Test
    void setStart() {
        eventOccurrence.setStart(Calendar.DATE, 2);
        calendar.set(1, 1, 2, 0, 0);
        assertThat(eventOccurrence.getStartDate(), equalTo(calendar.getTime()));
        calendar.set(1, 1, 2, 1, 0);
        assertThat(eventOccurrence.getEndDate(), equalTo(calendar.getTime()));
    }

}