package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static ru.nsu.ccfit.nsuschedule.domain.entities.OccursBetween.occursBetween;
import static ru.nsu.ccfit.nsuschedule.domain.entities.StartsBetweenMatcher.startsBetween;

class EventDateTest {
    private Calendar calendar;
    private Date startDate;
    private Date endDate;
    private EventOccurrence eventOccurrence;

    @BeforeEach
    void setUp() {
        calendar = Calendar.getInstance();
        calendar.set(1, Calendar.FEBRUARY, 2);
        startDate = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        endDate = calendar.getTime();
        eventOccurrence = new EventOccurrence(startDate, endDate);
    }

    @ParameterizedTest
    @EnumSource(Repeating.class)
    void noOccurrencesAfterRepeatUntilDate(Repeating repeating) {
        calendar.setTime(startDate);
        if (repeating.equals(Repeating.ONCE))
            calendar.add(Calendar.DATE, 1);
        else
            calendar.add(repeating.dateInterval, repeating.amount * 5);
        EventDate eventDate = new EventDate(startDate, endDate, repeating, calendar.getTime());
        assertThat(eventDate.getFirstOccurrenceAfterDate(calendar.getTime()), equalTo(Optional.empty()));
    }

    @Test
    void getFirstOccurrenceAfterDateRepeatsOnce() {
        EventDate eventDate = new EventDate(startDate, endDate, Repeating.ONCE);
        assertThat(eventDate.getFirstOccurrenceAfterDate(startDate), is(equalTo(Optional.of(eventOccurrence))));
        assertThat(eventOccurrence.startsBefore(endDate), is(true));
        assertThat(eventDate.getFirstOccurrenceAfterDate(endDate), equalTo(Optional.empty()));
    }

    @ParameterizedTest
    @EnumSource(Repeating.class)
    void firstOccurrenceAfterStartDateEqualToFirstOccurrence(Repeating repeating) {
        EventDate eventDate = new EventDate(startDate, endDate, repeating);
        assertThat(eventDate.getFirstOccurrenceAfterDate(startDate), equalTo(Optional.of(eventOccurrence)));
    }


    @ParameterizedTest
    @EnumSource(value = Repeating.class,
            names = {"ONCE"},
            mode = EnumSource.Mode.EXCLUDE)
    void getFirstOccurrenceAfterDate(Repeating repeating) {
        EventDate eventDate = new EventDate(startDate, endDate, repeating);
        eventOccurrence.moveToNext(repeating);
        Optional<EventOccurrence> expected = Optional.of(eventOccurrence),
                actual = eventDate.getFirstOccurrenceAfterDate(endDate);
        assertThat(actual, equalTo(expected));
    }

    @ParameterizedTest
    @EnumSource(value = Repeating.class,
            names = {"ONCE"},
            mode = EnumSource.Mode.EXCLUDE)
    void getOccurrencesBetweenDates(Repeating repeating) {
        int repeatingAmount = 5;
        calendar.setTime(startDate);
        calendar.add(repeating.dateInterval, repeating.amount * repeatingAmount);
        Date testDate = calendar.getTime();
        calendar.add(repeating.dateInterval, repeating.amount * repeatingAmount);
        Date repeatUntilDate = calendar.getTime();
        EventDate eventDate = new EventDate(startDate, endDate, repeating, repeatUntilDate);
        List<EventOccurrence> occurrencesBetweenDates = eventDate.getOccurrencesBetweenDates(startDate, testDate);
        assertThat(occurrencesBetweenDates, hasSize(repeatingAmount));
        assertThat(occurrencesBetweenDates, everyItem(startsBetween(startDate, testDate)));
    }

    @Test
    void occursBetweenDates() {
        Repeating repeating = Repeating.WEEK;
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 2);
        EventDate eventDate = new EventDate(startDate, endDate, repeating, calendar.getTime());
        assertThat(eventDate, occursBetween(startDate, endDate));
        eventOccurrence.moveToNext(repeating);
        assertThat(eventDate, occursBetween(eventOccurrence.getStartDate(), eventOccurrence.getEndDate()));
        eventOccurrence.setStart(Calendar.YEAR, 3);
        assertThat(eventDate, not(occursBetween(eventOccurrence.getStartDate(), eventOccurrence.getEndDate())));
    }
}